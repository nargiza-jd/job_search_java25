package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dto.UserDto;
import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final List<User> users = new ArrayList<>();
    private int userId = 1;

    @Override
    public void register(UserDto userDto) {
        User user = new User();
        user.setId(userId++);
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAccountType(userDto.getAccountType());
        users.add(user);
        log.info("Новый пользователь зарегистрирован: {}", user);
    }
}
