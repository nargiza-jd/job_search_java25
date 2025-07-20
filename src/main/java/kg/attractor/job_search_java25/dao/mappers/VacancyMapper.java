package kg.attractor.job_search_java25.dao.mappers;

import kg.attractor.job_search_java25.model.Vacancy;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class VacancyMapper implements RowMapper<Vacancy> {

    @Override
    public Vacancy mapRow(ResultSet rs, int rowNum) throws SQLException {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(rs.getInt("id"));
        vacancy.setName(rs.getString("name"));
        vacancy.setDescription(rs.getString("description"));
        vacancy.setCategoryId(rs.getInt("category_id"));
        vacancy.setSalary(rs.getDouble("salary"));
        vacancy.setExpFrom(rs.getInt("exp_from"));
        vacancy.setExpTo(rs.getInt("exp_to"));
        vacancy.setActive(rs.getBoolean("is_active"));
        vacancy.setAuthorId(rs.getInt("author_id"));
        vacancy.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        vacancy.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
        return vacancy;
    }
}