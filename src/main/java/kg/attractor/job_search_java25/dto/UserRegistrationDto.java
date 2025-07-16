package kg.attractor.job_search_java25.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String email;
    private String password;
    private String name;
    private String accountType;
}
