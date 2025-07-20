package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.UserDao;
import kg.attractor.job_search_java25.exceptions.UserNotFoundException;
import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с ID " + id + " не найден"));
    }

    @Override
    public User createUser(User user) {
        userDao.findByEmail(user.getEmail()).ifPresent(existing -> {
            throw new IllegalArgumentException("Email '" + user.getEmail() + "' уже существует");
        });

        int newUserId = userDao.addUser(user);
        return getUserById(newUserId);
    }

    @Override
    public User updateUser(int id, User updatedUser) {
        getUserById(id);

        updatedUser.setId(id);
        userDao.updateUser(id, updatedUser);
        return updatedUser;
    }

    @Override
    public boolean deleteUser(int id) {
        return userDao.deleteUser(id) > 0;
    }

    @Override
    public List<User> searchApplicants(String query) {
        return userDao.searchApplicants(query);
    }

    @Override
    public List<User> findByPhoneNumber(String phoneNumber) {
        return userDao.findByPhoneNumber(phoneNumber);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с email '" + email + "' не найден"));
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            user.setAge(rs.getInt("age"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPhoneNumber(rs.getString("phone_number"));
            user.setAvatar(rs.getString("avatar"));
            user.setAccountType(rs.getString("account_type"));
            return user;
        };
    }

    @Override
    public List<User> findByName(String name) {
        return userDao.findByName(name);
    }
}