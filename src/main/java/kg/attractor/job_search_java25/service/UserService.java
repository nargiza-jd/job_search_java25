package kg.attractor.job_search_java25.service;

import kg.attractor.job_search_java25.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



public interface UserService {
    List<User> getAllUsers();
    User getUserById(int id);
    User createUser(User user);
    User updateUser(int id, User updatedUser);
    boolean deleteUser(int id);
    List<User> searchApplicants(String query);
    List<User> findByPhoneNumber(String phoneNumber);
    User findByEmail(String email);

    List<User> findByName(String name);

    String saveAvatar(int userId, MultipartFile file) throws IOException;

    ResponseEntity<byte[]> getAvatar(int userId) throws IOException;
}