package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.model.User;
import kg.attractor.job_search_java25.service.UserService;
import kg.attractor.job_search_java25.util.FileUtil;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class UserServiceImpl implements UserService {
    private final FileUtil fileUtil;
    private List<User> users;

    public UserServiceImpl(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @PostConstruct
    public void init() {
        users = new CopyOnWriteArrayList<>(fileUtil.loadUsers());
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public Optional<User> getUserById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }

    @Override
    public User createUser(User user) {
        user.setId(fileUtil.getNextUserId());
        users.add(user);
        fileUtil.saveUsers(users);
        return user;
    }

    @Override
    public Optional<User> updateUser(int id, User updatedUser) {
        Optional<User> existingUserOpt = getUserById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setSurname(updatedUser.getSurname());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setAccountType(updatedUser.getAccountType());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setAvatar(updatedUser.getAvatar());
            existingUser.setPassword(updatedUser.getPassword());

            fileUtil.saveUsers(users);
            return Optional.of(existingUser);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteUser(int id) {
        boolean removed = users.removeIf(user -> user.getId() == id);
        if (removed) {
            fileUtil.saveUsers(users);
        }
        return removed;
    }

    @Override
    public List<User> searchApplicants(String query) {
        return users.stream()
                .filter(user -> "APPLICANT".equalsIgnoreCase(user.getAccountType()))
                .filter(user ->
                        user.getName().toLowerCase().contains(query.toLowerCase()) ||
                                user.getEmail().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }
}