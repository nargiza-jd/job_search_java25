package kg.attractor.job_search_java25.dao;

import kg.attractor.job_search_java25.model.EducationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EducationInfoDao {
    private final JdbcTemplate jdbc;

    public List<EducationInfo> getByResumeId(int resumeId) {
        String sql = "SELECT * FROM education_info WHERE resume_id = ?";
        return jdbc.query(sql, (rs, rowNum) -> {
            EducationInfo info = new EducationInfo();
            info.setId(rs.getInt("id"));
            info.setResumeId(rs.getInt("resume_id"));
            info.setInstitution(rs.getString("institution"));
            info.setProgram(rs.getString("program"));
            info.setStartDate(rs.getDate("start_date").toLocalDate());
            info.setEndDate(rs.getDate("end_date").toLocalDate());
            info.setDegree(rs.getString("degree"));
            return info;
        }, resumeId);
    }

    public void delete(int id) {
        String sql = "DELETE FROM education_info WHERE id = ?";
        jdbc.update(sql, id);
    }

    public void saveAll(int resumeId, List<EducationInfo> educationList) {
        String sql = """
            INSERT INTO education_info (resume_id, institution, program, start_date, end_date, degree)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        for (EducationInfo edu : educationList) {
            jdbc.update(sql,
                    resumeId,
                    edu.getInstitution(),
                    edu.getProgram(),
                    edu.getStartDate(),
                    edu.getEndDate(),
                    edu.getDegree());
        }
    }
}