package kg.attractor.job_search_java25.dao;

import kg.attractor.job_search_java25.dao.mappers.UserEntityMapper;
import kg.attractor.job_search_java25.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserEntityMapper userEntityMapper;

    public List<User> getAllUsers() {
        String sql = "SELECT id, name, surname, age, email, password, phone_number, avatar, account_type FROM users";
        return jdbcTemplate.query(sql, userEntityMapper);
    }

    public Optional<User> findById(int id) {
        String sql = "SELECT id, name, surname, age, email, password, phone_number, avatar, account_type FROM users WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, userEntityMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id, name, surname, age, email, password, phone_number, avatar, account_type FROM users WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, userEntityMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public User saveUser(User user) {
        String sql = "INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getSurname(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAvatar(),
                user.getAccountType());

        User savedUser = findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("Не удалось найти сохраненного пользователя по email!"));
        return savedUser;
    }

    public User updateUser(User user) {
        String sql = "UPDATE users SET name = ?, surname = ?, age = ?, email = ?, password = ?, phone_number = ?, avatar = ?, account_type = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getSurname(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAvatar(),
                user.getAccountType(),
                user.getId());
        return user;
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public List<User> searchApplicants(String query) {
        String sql = "SELECT id, name, surname, age, email, password, phone_number, avatar, account_type FROM users " +
                "WHERE account_type = 'APPLICANT' AND (name ILIKE ? OR surname ILIKE ? OR email ILIKE ?)";
        String likeQuery = "%" + query + "%";
        return jdbcTemplate.query(sql, userEntityMapper, likeQuery, likeQuery, likeQuery);
    }

    public List<User> findByPhoneNumber(String phoneNumber) {
        String sql = "SELECT id, name, surname, age, email, password, phone_number, avatar, account_type FROM users WHERE phone_number = ?";
        return jdbcTemplate.query(sql, userEntityMapper, phoneNumber);
    }

    public List<User> findByName(String name) {
        String sql = "SELECT id, name, surname, age, email, password, phone_number, avatar, account_type FROM users WHERE name ILIKE ?";
        return jdbcTemplate.query(sql, userEntityMapper, "%" + name + "%");
    }
}