package kg.attractor.job_search_java25.dao.mappers;

import kg.attractor.job_search_java25.dto.UserDto;
import kg.attractor.job_search_java25.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<UserDto> {
    @Override
    public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        return UserDto.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .age(rs.getInt("age"))
                .email(rs.getString("email"))
                .phoneNumber(rs.getString("phone_number"))
                .avatar(rs.getString("avatar"))
                .accountType(rs.getString("account_type"))
                .build();
    }
}