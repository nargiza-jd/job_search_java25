package kg.attractor.job_search_java25.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String accountType;
}