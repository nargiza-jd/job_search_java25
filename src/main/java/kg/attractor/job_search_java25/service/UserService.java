package kg.attractor.job_search_java25.service;

import kg.attractor.job_search_java25.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(int id);
    User createUser(User user);
    Optional<User> updateUser(int id, User user);
    boolean deleteUser(int id);

    List<User> searchApplicants(String query);
}