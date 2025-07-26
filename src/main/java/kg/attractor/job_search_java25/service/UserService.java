package kg.attractor.job_search_java25.service;

import kg.attractor.job_search_java25.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(int id);
    UserDto createUser(UserRegistrationDto dto);
    UserDto updateUser(int id, UserProfileUpdateDto dto);
    boolean deleteUser(int id);
    List<UserDto> searchApplicants(String query);
    List<UserDto> findByPhoneNumber(String phoneNumber);
    Optional<UserDto> findByEmail(String email);
    List<UserDto> findByName(String name);
    String saveAvatar(int userId, MultipartFile file) throws IOException;
    ResponseEntity<byte[]> getAvatar(int userId) throws IOException;
}