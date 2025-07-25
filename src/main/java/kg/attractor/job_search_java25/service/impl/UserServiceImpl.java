package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.UserDao;
import kg.attractor.job_search_java25.dto.*;
import kg.attractor.job_search_java25.exceptions.UserNotFoundException;
import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.service.UserService;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAvatar(user.getAvatar());
        dto.setAccountType(user.getAccountType());
        return dto;
    }

    private User fromRegistrationDto(UserRegistrationDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setAccountType(dto.getAccountType());
        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userDao.getAllUsers().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(int id) {
        User user = userDao.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
        return toDto(user);
    }

    @Override
    public UserDto createUser(UserRegistrationDto dto) {
        userDao.findByEmail(dto.getEmail()).ifPresent(existing -> {
            throw new IllegalArgumentException("Email '" + dto.getEmail() + "' уже существует");
        });

        User user = fromRegistrationDto(dto);
        int id = userDao.addUser(user);
        return getUserById(id);
    }

    @Override
    public UserDto updateUser(int id, UserProfileUpdateDto dto) {
        User existing = userDao.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        existing.setName(dto.getName());
        existing.setSurname(dto.getSurname());
        existing.setAge(dto.getAge());
        existing.setPhoneNumber(dto.getPhoneNumber());

        userDao.updateUser(id, existing);
        return toDto(existing);
    }

    @Override
    public boolean deleteUser(int id) {
        return userDao.deleteUser(id) > 0;
    }

    @Override
    public List<UserDto> searchApplicants(String query) {
        return userDao.searchApplicants(query).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findByPhoneNumber(String phoneNumber) {
        return userDao.findByPhoneNumber(phoneNumber).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        return userDao.findByEmail(email).map(this::toDto);
    }

    @Override
    public List<UserDto> findByName(String name) {
        return userDao.findByName(name).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String saveAvatar(int userId, MultipartFile file) throws IOException {
        User user = userDao.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        String uploadDir = "uploads/avatars/";
        Files.createDirectories(Paths.get(uploadDir));

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, filename);
        Files.write(path, file.getBytes());

        user.setAvatar(filename);
        userDao.updateUser(userId, user);

        return filename;
    }

    @Override
    public ResponseEntity<byte[]> getAvatar(int userId) throws IOException {
        User user = userDao.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        String filename = user.getAvatar();
        if (filename == null || filename.isBlank()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        Path path = Paths.get("uploads/avatars", filename);
        byte[] image = Files.readAllBytes(path);

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        if (filename.endsWith(".png")) mediaType = MediaType.IMAGE_PNG;
        else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) mediaType = MediaType.IMAGE_JPEG;
        else if (filename.endsWith(".gif")) mediaType = MediaType.IMAGE_GIF;

        return ResponseEntity.ok().contentType(mediaType).body(image);
    }
}