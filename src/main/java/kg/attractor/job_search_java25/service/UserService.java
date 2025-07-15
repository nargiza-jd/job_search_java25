package kg.attractor.job_search_java25.service;

import kg.attractor.job_search_java25.dto.UserRegistrationDto;
import kg.attractor.job_search_java25.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int id);
    void createUser(UserRegistrationDto userDto);
}