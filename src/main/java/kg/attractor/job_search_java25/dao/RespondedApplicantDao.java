package kg.attractor.job_search_java25.dao;

import kg.attractor.job_search_java25.model.RespondedApplicant;
import kg.attractor.job_search_java25.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class RespondedApplicantDao {
    private final JdbcTemplate jdbcTemplate;

    public RespondedApplicant save(int resumeId, int vacancyId, int applicantId) {
        String sql = "INSERT INTO responded_applicants (resume_id, vacancy_id, applicant_id, confirmation) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setInt(1, resumeId);
            ps.setInt(2, vacancyId);
            ps.setInt(3, applicantId);
            ps.setBoolean(4, false);
            return ps;
        }, keyHolder);

        RespondedApplicant ra = new RespondedApplicant();
        ra.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        ra.setResumeId(resumeId);
        ra.setVacancyId(vacancyId);
        ra.setApplicantId(applicantId);
        ra.setConfirmation(false);

        return ra;
    }

    public List<RespondedApplicant> findAll() {
        String sql = "SELECT * FROM responded_applicants";
        return jdbcTemplate.query(sql, getMapper());
    }

    public List<RespondedApplicant> findByVacancyId(int vacancyId) {
        String sql = "SELECT * FROM responded_applicants WHERE vacancy_id = ?";
        return jdbcTemplate.query(sql, getMapper(), vacancyId);
    }

    public List<RespondedApplicant> findByResumeId(int resumeId) {
        String sql = "SELECT * FROM responded_applicants WHERE resume_id = ?";
        return jdbcTemplate.query(sql, getMapper(), resumeId);
    }

    public List<Integer> getVacancyIdsByApplicantId(int applicantId) {
        String sql = """
        SELECT DISTINCT ra.vacancy_id
        FROM responded_applicants ra
        JOIN resumes r ON ra.resume_id = r.id
        WHERE r.applicant_id = ?
    """;
        return jdbcTemplate.queryForList(sql, Integer.class, applicantId);
    }

    public List<User> getApplicantsByVacancyId(int vacancyId) {
        String sql = """
            SELECT u.* FROM users u
            JOIN resumes r ON u.id = r.applicant_id
            JOIN responded_applicants ra ON ra.resume_id = r.id
            WHERE ra.vacancy_id = ?
            """;

        return jdbcTemplate.query(sql, new Object[]{vacancyId}, (rs, rowNum) -> {
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
        });
    }

    private RowMapper<RespondedApplicant> getMapper() {
        return (rs, rowNum) -> {
            RespondedApplicant ra = new RespondedApplicant();
            ra.setId(rs.getInt("id"));
            ra.setResumeId(rs.getInt("resume_id"));
            ra.setVacancyId(rs.getInt("vacancy_id"));
            ra.setApplicantId(rs.getInt("applicant_id"));
            ra.setConfirmation(rs.getBoolean("confirmation"));

            if (rs.getTimestamp("response_date") != null) {
                ra.setResponseDate(rs.getTimestamp("response_date").toLocalDateTime());
            }
            return ra;
        };
    }
}