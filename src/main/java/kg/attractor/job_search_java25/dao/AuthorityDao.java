package kg.attractor.job_search_java25.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorityDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Integer> findAuthorityIdByName(String name) {
        String sql = "SELECT id FROM authorities WHERE name = ?";
        try {
            Integer id = jdbcTemplate.queryForObject(sql, Integer.class, name);
            return Optional.ofNullable(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void addUserAuthority(int userId, int authorityId) {
        String sql = "INSERT INTO user_authorities (user_id, authority_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, authorityId);
    }
}