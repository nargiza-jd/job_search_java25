package kg.attractor.job_search_java25.service;

import kg.attractor.job_search_java25.dto.UserRegistrationDto;
import kg.attractor.job_search_java25.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(int id);
    User createUser(User user);
    Optional<User> updateUser(int id, User updatedUser);
    boolean deleteUser(int id);
}