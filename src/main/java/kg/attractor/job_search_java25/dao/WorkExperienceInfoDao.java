package kg.attractor.job_search_java25.dao;

import kg.attractor.job_search_java25.model.WorkExperienceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WorkExperienceInfoDao {
    private final JdbcTemplate jdbc;

    public List<WorkExperienceInfo> getByResumeId(int resumeId) {
        String sql = "SELECT * FROM work_experience_info WHERE resume_id = ?";
        return jdbc.query(sql, (rs, rowNum) -> {
            WorkExperienceInfo info = new WorkExperienceInfo();
            info.setId(rs.getInt("id"));
            info.setResumeId(rs.getInt("resume_id"));
            info.setYears(rs.getInt("years"));
            info.setCompanyName(rs.getString("company_name"));
            info.setPosition(rs.getString("position"));
            info.setResponsibilities(rs.getString("responsibilities"));
            return info;
        }, resumeId);
    }

    public void delete(int id) {
        String sql = "DELETE FROM work_experience_info WHERE id = ?";
        jdbc.update(sql, id);
    }

    public void saveAll(int resumeId, List<WorkExperienceInfo> workList) {
        String sql = """
            INSERT INTO work_experience_info (resume_id, years, company_name, position, responsibilities)
            VALUES (?, ?, ?, ?, ?)
        """;
        for (WorkExperienceInfo w : workList) {
            jdbc.update(sql,
                    resumeId,
                    w.getYears(),
                    w.getCompanyName(),
                    w.getPosition(),
                    w.getResponsibilities());
        }
    }
}