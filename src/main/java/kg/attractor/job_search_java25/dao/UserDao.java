package kg.attractor.job_search_java25.dao;

import kg.attractor.job_search_java25.dao.mappers.UserMapper;
import kg.attractor.job_search_java25.dto.UserDto;
import kg.attractor.job_search_java25.dto.UserRegistrationDto;
import kg.attractor.job_search_java25.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public List<UserDto> getAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userMapper);
    }

    public Optional<UserDto> getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql, userMapper, id)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public UserDto addUser(UserRegistrationDto registrationDto) {

        String sql = "INSERT INTO users (name, surname, age, email, password, phone_number, avatar, account_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, registrationDto.getName());
            ps.setString(2, registrationDto.getSurname());
            ps.setObject(3, registrationDto.getAge());
            ps.setString(4, registrationDto.getEmail());
            ps.setString(5, passwordEncoder.encode(registrationDto.getPassword()));
            ps.setObject(6, registrationDto.getPhoneNumber());
            ps.setString(7, registrationDto.getAvatar());
            ps.setString(8, registrationDto.getAccountType());
            return ps;
        }, keyHolder);

        int generatedId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return UserDto.builder()
                .id(generatedId)
                .name(registrationDto.getName())
                .surname(registrationDto.getSurname())
                .age(registrationDto.getAge())
                .email(registrationDto.getEmail())
                .phoneNumber(registrationDto.getPhoneNumber())
                .avatar(registrationDto.getAvatar())
                .accountType(registrationDto.getAccountType())
                .build();
    }


    public int updateUser(int id, User user) {
        String sql = "UPDATE users SET name = ?, surname = ?, age = ?, email = ?, password = ?, phone_number = ?, avatar = ?, account_type = ? WHERE id = ?";

        return jdbcTemplate.update(sql,
                user.getName(),
                user.getSurname(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAvatar(),
                user.getAccountType(),
                id
        );
    }

    public int updateUserFromDto(int id, UserDto userDto) {
        String sql = "UPDATE users SET name = ?, surname = ?, age = ?, email = ?, phone_number = ?, avatar = ?, account_type = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                userDto.getName(),
                userDto.getSurname(),
                userDto.getAge(),
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getAvatar(),
                userDto.getAccountType(),
                id
        );
    }

    public int updatePassword(int id, String newRawPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        return jdbcTemplate.update(sql, passwordEncoder.encode(newRawPassword), id);
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public List<UserDto> searchApplicants(String query) {
        String sql = "SELECT * FROM users WHERE account_type = 'applicant' AND (LOWER(name) LIKE :q OR LOWER(email) LIKE :q)";
        return namedParameterJdbcTemplate.query(
                sql,
                new MapSqlParameterSource("q", "%" + query.toLowerCase() + "%"),
                userMapper
        );
    }

    public List<UserDto> findByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM users WHERE phone_number LIKE ?";
        return jdbcTemplate.query(sql, userMapper, "%" + phoneNumber + "%");
    }

    public Optional<UserDto> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql, userMapper, email)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<UserDto> findByName(String name) {
        String sql = "SELECT * FROM users WHERE LOWER(name) LIKE ?";
        return jdbcTemplate.query(sql, userMapper, "%" + name.toLowerCase() + "%");
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}