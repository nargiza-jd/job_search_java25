package kg.attractor.job_search_java25.dao;

import kg.attractor.job_search_java25.dao.mappers.ResumeMapper;
import kg.attractor.job_search_java25.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;

    public List<Resume> getAll() {
        String sql = "SELECT * FROM resumes";
        return jdbcTemplate.query(sql, new ResumeMapper());
    }

    public Optional<Resume> getById(int id) {
        String sql = "SELECT * FROM resumes WHERE id = ?";
        return Optional.ofNullable(
                DataAccessUtils.singleResult(
                        jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), id)
                )
        );
    }

    public List<Resume> getByCategory(int categoryId) {
        String sql = "SELECT * FROM resumes WHERE category_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), categoryId);
    }

    public List<Resume> getByApplicantId(int applicantId) {
        String sql = "SELECT * FROM resumes WHERE applicant_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), applicantId);
    }

    public List<Resume> searchByName(String name) {
        String sql = "SELECT * FROM resumes WHERE LOWER(name) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Resume.class), "%" + name + "%");
    }

    public Resume save(Resume resume) {
        String sql = "INSERT INTO resumes (name, category_id, salary, applicant_id, is_active, created_date, update_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"ID"});

            ps.setString(1, resume.getName());
            ps.setInt(2, resume.getCategoryId());
            ps.setDouble(3, resume.getSalary());
            ps.setInt(4, resume.getApplicantId());
            ps.setBoolean(5, resume.isActive());
            ps.setTimestamp(6, Timestamp.valueOf(resume.getCreatedDate()));
            ps.setTimestamp(7, Timestamp.valueOf(resume.getUpdateTime()));
            return ps;
        }, keyHolder);

        resume.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return resume;
    }

    public void update(int id, Resume resume) {
        String sql = """
        UPDATE resumes
        SET name = ?, category_id = ?, salary = ?, is_active = ?
        WHERE id = ?
        """;
        jdbcTemplate.update(sql,
                resume.getName(),
                resume.getCategoryId(),
                resume.getSalary(),
                resume.isActive(),
                id
        );
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM resumes WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM resumes WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}