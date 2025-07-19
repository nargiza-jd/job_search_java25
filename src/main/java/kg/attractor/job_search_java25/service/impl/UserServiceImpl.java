package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.dao.UserDao;
import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public User createUser(User user) {
        int id = userDao.addUser(user);
        user.setId(id);
        return user;
    }

    @Override
    public Optional<User> updateUser(int id, User updatedUser) {
        Optional<User> existingUserOpt = userDao.getUserById(id);
        if (existingUserOpt.isPresent()) {
            userDao.updateUser(id, updatedUser);
            updatedUser.setId(id);
            return Optional.of(updatedUser);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteUser(int id) {
        return userDao.deleteUser(id) > 0;
    }

    @Override
    public List<User> searchApplicants(String query) {
        return userDao.searchApplicants(query);
    }
}