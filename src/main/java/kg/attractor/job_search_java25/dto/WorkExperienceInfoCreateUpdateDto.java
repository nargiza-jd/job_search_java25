package kg.attractor.job_search_java25.dto;

import lombok.Data;

@Data
public class WorkExperienceInfoCreateUpdateDto {
    private Integer id;
    private int years;
    private String companyName;
    private String position;
    private String responsibilities;
}