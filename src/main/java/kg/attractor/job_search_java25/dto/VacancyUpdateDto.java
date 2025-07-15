package kg.attractor.job_search_java25.dto;

import lombok.Data;

@Data
public class VacancyUpdateDto {
    private String name;
    private String description;
    private int categoryId;
    private double salary;
    private int expFrom;
    private int expTo;
    private boolean isActive;
}