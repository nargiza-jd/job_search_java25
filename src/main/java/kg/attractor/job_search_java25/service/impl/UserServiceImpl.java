package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dto.UserRegistrationDto;
import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService {

    private final List<User> users = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User getUserById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void createUser(UserRegistrationDto dto) {
        User user = new User();
        user.setId(idCounter.getAndIncrement());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setAge(dto.getAge());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAccountType(dto.getAccountType());
        user.setAvatar(dto.getAvatar());

        users.add(user);
    }
}