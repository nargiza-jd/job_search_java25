package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.AuthorityDao;
import kg.attractor.job_search_java25.dao.UserDao;
import kg.attractor.job_search_java25.dto.*;
import kg.attractor.job_search_java25.exceptions.UserNotFoundException;
import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final AuthorityDao authorityDao;
    private final PasswordEncoder passwordEncoder;

    private static final String UPLOAD_DIR = "uploads/avatars/";

    private UserDto convertToDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userDao.getAllUsers().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(int id) {
        return userDao.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
    }

    @Override
    public UserDto createUser(UserRegistrationDto dto) {
        userDao.findByEmail(dto.getEmail()).ifPresent(existingUser -> {
            throw new IllegalArgumentException("Email '" + dto.getEmail() + "' уже существует");
        });

        User newUser = new User();
        newUser.setName(dto.getName());
        newUser.setSurname(dto.getSurname());
        newUser.setAge(dto.getAge());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setPhoneNumber(dto.getPhoneNumber());
        newUser.setAvatar(null);
        newUser.setAccountType(dto.getAccountType());

        User createdUserModel = userDao.saveUser(newUser);
        UserDto createdUserDto = convertToDto(createdUserModel);

        String roleName = "ROLE_" + dto.getAccountType().toUpperCase();
        authorityDao.findAuthorityIdByName(roleName)
                .ifPresentOrElse(
                        authorityId -> authorityDao.addUserAuthority(createdUserDto.getId(), authorityId),
                        () -> System.err.println("Предупреждение: Роль '" + roleName + "' не найдена в базе данных для нового пользователя " + dto.getEmail())
                );
        return createdUserDto;
    }

    @Override
    public UserDto updateUser(int id, UserProfileUpdateDto dto) {
        User existingUserModel = userDao.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        existingUserModel.setName(dto.getName());
        existingUserModel.setSurname(dto.getSurname());
        existingUserModel.setAge(dto.getAge());
        existingUserModel.setPhoneNumber(dto.getPhoneNumber());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingUserModel.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User updatedUserModel = userDao.updateUser(existingUserModel);
        return convertToDto(updatedUserModel);
    }

    @Override
    public boolean deleteUser(int id) {
        return userDao.deleteUser(id);
    }

    @Override
    public List<UserDto> searchApplicants(String query) {
        return userDao.searchApplicants(query).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findByPhoneNumber(String phoneNumber) {
        return userDao.findByPhoneNumber(phoneNumber).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        return userDao.findByEmail(email)
                .map(this::convertToDto);
    }

    @Override
    public List<UserDto> findByName(String name) {
        return userDao.findByName(name).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public String saveAvatar(int userId, MultipartFile file) throws IOException {
        User existingUser = userDao.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден для обновления аватара."));

        Path uploadPath = Paths.get(UPLOAD_DIR);
        Files.createDirectories(uploadPath);

        String filename = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            filename += originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        Path path = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        existingUser.setAvatar(filename);
        userDao.updateUser(existingUser);

        return filename;
    }

    @Override
    public ResponseEntity<byte[]> getAvatar(int userId) throws IOException {
        UserDto userDto = getUserById(userId);

        String filename = userDto.getAvatar();
        if (filename == null || filename.isBlank()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        Path path = Paths.get(UPLOAD_DIR, filename);

        if (!Files.exists(path)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        byte[] image = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
        String contentType = null;
        try {
            contentType = Files.probeContentType(path);
        } catch (IOException e) {
            System.err.println("Error probing content type for " + filename + ": " + e.getMessage());
        }
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(image.length);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}