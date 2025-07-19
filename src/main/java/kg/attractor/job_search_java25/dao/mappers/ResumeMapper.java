package kg.attractor.job_search_java25.dao.mappers;

import kg.attractor.job_search_java25.model.Resume;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResumeMapper implements RowMapper<Resume> {
    @Override
    public Resume mapRow(ResultSet rs, int rowNum) throws SQLException {
        Resume resume = new Resume();
        resume.setId(rs.getInt("id"));
        resume.setApplicantId(rs.getInt("applicant_id"));
        resume.setName(rs.getString("name"));
        resume.setCategoryId(rs.getInt("category_id"));
        resume.setSalary(rs.getDouble("salary"));
        resume.setActive(rs.getBoolean("is_active"));
        resume.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        resume.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
        return resume;
    }
}