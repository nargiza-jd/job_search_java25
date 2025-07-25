package kg.attractor.job_search_java25.dao;

import kg.attractor.job_search_java25.model.WorkExperienceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkExperienceInfoDao {
    private final JdbcTemplate jdbcTemplate;

    public void saveAll(int resumeId, List<WorkExperienceInfo> workList) {
        String sql = "INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities) " +
                "VALUES (?, ?, ?, ?, ?)";
        for (WorkExperienceInfo w : workList) {
            jdbcTemplate.update(sql, resumeId, w.getYears(), w.getCompanyName(),
                    w.getPosition(), w.getResponsibilities());
        }
    }
}