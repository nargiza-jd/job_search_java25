package kg.attractor.job_search_java25.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationInfoCreateUpdateDto {
    private Integer id;

    @NotBlank(message = "Institution name must not be blank")
    @Size(max = 100, message = "Institution name must be less than 100 characters")
    private String institution;

    @NotBlank(message = "Program name must not be blank")
    @Size(max = 100, message = "Program name must be less than 100 characters")
    private String program;

    @PastOrPresent(message = "Start date must be in the past or today")
    private LocalDate startDate;

    @PastOrPresent(message = "End date must be in the past or today")
    private LocalDate endDate;

    @NotBlank(message = "Degree must not be blank")
    @Size(max = 100, message = "Degree must be less than 100 characters")
    private String degree;
}