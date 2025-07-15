package kg.attractor.job_search_java25.dto;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String surname;
    private int age;
    private String email;
    private String password;
    private String phoneNumber;
    private String accountType;
}
