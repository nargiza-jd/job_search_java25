package kg.attractor.job_search_java25.dto;

import lombok.Data;

@Data
public class VacancyCreateDto {
    private String name;
    private String description;
    private int categoryId;
    private double salary;
    private int expFrom;
    private int expTo;
}