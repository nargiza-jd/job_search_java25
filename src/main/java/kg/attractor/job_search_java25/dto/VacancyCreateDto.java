package kg.attractor.job_search_java25.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyCreateDto {
    private String name;
    private String description;
    private int categoryId;
    private double salary;
    private int expFrom;
    private int expTo;
    private int authorId;
}