package kg.attractor.job_search_java25.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EducationInfoCreateUpdateDto {
    private Integer id;
    private String institution;
    private String program;
    private LocalDate startDate;
    private LocalDate endDate;
    private String degree;
}