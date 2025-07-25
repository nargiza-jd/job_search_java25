package kg.attractor.job_search_java25.dao;

import kg.attractor.job_search_java25.model.EducationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EducationInfoDao {
    private final JdbcTemplate jdbcTemplate;

    public void saveAll(int resumeId, List<EducationInfo> educationList) {
        String sql = "INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        for (EducationInfo e : educationList) {
            jdbcTemplate.update(sql, resumeId, e.getInstitution(), e.getProgram(),
                    e.getStartDate(), e.getEndDate(), e.getDegree());
        }
    }
}