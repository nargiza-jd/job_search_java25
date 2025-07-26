package kg.attractor.job_search_java25.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WorkExperienceInfoCreateUpdateDto {

    private Integer id;

    @Min(value = 0, message = "Years must be 0 or more")
    private int years;

    @NotBlank(message = "Company name must not be blank")
    @Size(max = 100, message = "Company name must be at most 100 characters")
    private String companyName;

    @NotBlank(message = "Position must not be blank")
    @Size(max = 100, message = "Position must be at most 100 characters")
    private String position;

    @NotBlank(message = "Responsibilities must not be blank")
    @Size(max = 1000, message = "Responsibilities must be at most 1000 characters")
    private String responsibilities;
}