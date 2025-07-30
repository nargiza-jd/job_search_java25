package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.UserDao;
import kg.attractor.job_search_java25.dto.*;
import kg.attractor.job_search_java25.exceptions.UserNotFoundException;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

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

    private static final String UPLOAD_DIR = "uploads/avatars/";

    @Override
    public String saveAvatar(int userId, MultipartFile file) throws IOException {
        UserDto userDto = userDao.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        Files.createDirectories(Paths.get(UPLOAD_DIR));

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR, filename);
        Files.write(path, file.getBytes());

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
        byte[] image = Files.readAllBytes(path);

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        if (filename.endsWith(".png")) mediaType = MediaType.IMAGE_PNG;
        else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) mediaType = MediaType.IMAGE_JPEG;
        else if (filename.endsWith(".gif")) mediaType = MediaType.IMAGE_GIF;

        return ResponseEntity.ok().contentType(mediaType).body(image);
    }
}