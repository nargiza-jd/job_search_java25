package kg.attractor.job_search_java25.dto;

import lombok.Data;

@Data
public class UserProfileUpdateDto {
    private String name;
    private String surname;
    private int age;
    private String phoneNumber;
}