package kg.attractor.job_search_java25.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public List<String> getAuthoritiesByUserId(int userId) {
        String sql = "SELECT a.name FROM authorities a JOIN user_authorities ua ON a.id = ua.authority_id WHERE ua.user_id = ?";
        return jdbcTemplate.queryForList(sql, String.class, userId);
    }
}