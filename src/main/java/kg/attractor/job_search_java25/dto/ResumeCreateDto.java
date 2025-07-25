package kg.attractor.job_search_java25.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ResumeCreateDto {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @Min(value = 1, message = "Category ID must be a positive number")
    private int categoryId;

    @Min(value = 0, message = "Salary must be non-negative")
    private double salary;

    @Min(value = 1, message = "Applicant ID must be a positive number")
    private int applicantId;

    @Valid
    private List<EducationInfoCreateUpdateDto> educationList;

    @Valid
    private List<WorkExperienceInfoCreateUpdateDto> workExperienceList;
}