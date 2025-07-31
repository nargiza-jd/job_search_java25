package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.AuthorityDao;
import kg.attractor.job_search_java25.dao.UserDao;
import kg.attractor.job_search_java25.dto.*;
import kg.attractor.job_search_java25.exceptions.UserNotFoundException;
import kg.attractor.job_search_java25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final AuthorityDao authorityDao;

    private static final String UPLOAD_DIR = "uploads/avatars/";

    @Override
    public List<UserDto> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public UserDto getUserById(int id) {
        return userDao.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
    }

    @Override
    public UserDto createUser(UserRegistrationDto dto) {
        userDao.findByEmail(dto.getEmail()).ifPresent(existing -> {
            throw new IllegalArgumentException("Email '" + dto.getEmail() + "' уже существует");
        });

        UserDto createdUser = userDao.addUser(dto);

        String roleName = "ROLE_" + dto.getAccountType().toUpperCase();
        authorityDao.findAuthorityIdByName(roleName)
                .ifPresentOrElse(
                        authorityId -> authorityDao.addUserAuthority(createdUser.getId(), authorityId),
                        () -> System.err.println("Предупреждение: Роль '" + roleName + "' не найдена в базе данных для нового пользователя " + dto.getEmail())
                );
        return createdUser;
    }

    @Override
    public UserDto updateUser(int id, UserProfileUpdateDto dto) {
        UserDto existingUserDto = userDao.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        existingUserDto.setName(dto.getName());
        existingUserDto.setSurname(dto.getSurname());
        existingUserDto.setAge(dto.getAge());
        existingUserDto.setPhoneNumber(dto.getPhoneNumber());
        userDao.updateUserFromDto(id, existingUserDto);

        return existingUserDto;
    }
    @Override
    public boolean deleteUser(int id) {
        return userDao.deleteUser(id);
    }

    @Override
    public List<UserDto> searchApplicants(String query) {
        return userDao.searchApplicants(query);
    }

    @Override
    public List<UserDto> findByPhoneNumber(String phoneNumber) {
        return userDao.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public List<UserDto> findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public String saveAvatar(int userId, MultipartFile file) throws IOException {
        UserDto userDto = userDao.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        Path uploadPath = Paths.get(UPLOAD_DIR);
        Files.createDirectories(uploadPath);

        String filename = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            filename += originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        Path path = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        userDto.setAvatar(filename);
        userDao.updateUserFromDto(userId, userDto);

        return filename;
    }

    @Override
    public ResponseEntity<byte[]> getAvatar(int userId) throws IOException {
        UserDto userDto = userDao.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

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
        }
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(image.length);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}