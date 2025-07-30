package kg.attractor.job_search_java25.dao;

import kg.attractor.job_search_java25.dao.mappers.VacancyMapper;
import kg.attractor.job_search_java25.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class VacancyDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Vacancy> getAllActive() {
        String sql = "SELECT * FROM vacancies WHERE is_active = true";
        return jdbcTemplate.query(sql, new VacancyMapper());
    }

    public List<Vacancy> getByAuthorId(int authorId) {
        String sql = "SELECT * FROM vacancies WHERE author_id = ?";
        return jdbcTemplate.query(sql, new VacancyMapper(), authorId);
    }

    public List<Vacancy> getByCategoryId(int categoryId) {
        String sql = "SELECT * FROM vacancies WHERE category_id = ? AND is_active = true";
        return jdbcTemplate.query(sql, new VacancyMapper(), categoryId);
    }

    public List<Vacancy> getByIds(List<Integer> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sql = "SELECT * FROM vacancies WHERE id IN (" + placeholders + ")";
        return jdbcTemplate.query(sql, new VacancyMapper(), ids.toArray());
    }

    public Vacancy getById(int id) {
        String sql = "SELECT * FROM vacancies WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new VacancyMapper(), id);
    }

    public int save(Vacancy vacancy) {
        String sql = "INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vacancy.getName());
            ps.setString(2, vacancy.getDescription());
            ps.setInt(3, vacancy.getCategoryId());
            ps.setDouble(4, vacancy.getSalary());
            ps.setInt(5, vacancy.getExpFrom());
            ps.setInt(6, vacancy.getExpTo());
            ps.setBoolean(7, vacancy.isActive());
            ps.setInt(8, vacancy.getAuthorId());
            ps.setTimestamp(9, Timestamp.valueOf(vacancy.getCreatedDate()));
            ps.setTimestamp(10, Timestamp.valueOf(vacancy.getUpdateTime()));
            return ps;
        }, keyHolder);

        if (keyHolder.getKeys() != null && keyHolder.getKeys().containsKey("ID")) {
            return ((Number) keyHolder.getKeys().get("ID")).intValue();
        } else {
            throw new RuntimeException("Failed to retrieve generated ID 'ID' for Vacancy.");
        }
    }

    public void update(Vacancy vacancy) {
        String sql = "UPDATE vacancies SET name=?, description=?, category_id=?, salary=?, exp_from=?, exp_to=?, is_active=?, update_time=? WHERE id=?";
        jdbcTemplate.update(sql,
                vacancy.getName(),
                vacancy.getDescription(),
                vacancy.getCategoryId(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.isActive(),
                vacancy.getUpdateTime(),
                vacancy.getId());
    }

    public void delete(int id) {
        String sql = "DELETE FROM vacancies WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void toggleStatus(int id, boolean isActive) {
        String sql = "UPDATE vacancies SET is_active = ?, update_time = CURRENT_TIMESTAMP WHERE id = ?";
        jdbcTemplate.update(sql, isActive, id);
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM vacancies WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}