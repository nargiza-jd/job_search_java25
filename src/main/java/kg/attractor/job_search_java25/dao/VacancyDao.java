package kg.attractor.job_search_java25.dao;

import kg.attractor.job_search_java25.dao.mappers.VacancyMapper;
import kg.attractor.job_search_java25.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

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

    public void save(Vacancy vacancy) {
        String sql = "INSERT INTO vacancies (name, description, category_id, salary, exp_from, exp_to, is_active, author_id, created_date, update_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                vacancy.getName(),
                vacancy.getDescription(),
                vacancy.getCategoryId(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.isActive(),
                vacancy.getAuthorId(),
                vacancy.getCreatedDate(),
                vacancy.getUpdateTime());
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
}