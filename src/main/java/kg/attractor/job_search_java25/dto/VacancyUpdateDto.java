package kg.attractor.job_search_java25.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VacancyUpdateDto {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Description must not be blank")
    @Size(max = 2000, message = "Description must be at most 2000 characters")
    private String description;

    @Min(value = 1, message = "Category ID must be a positive number")
    private int categoryId;

    @Min(value = 0, message = "Salary must not be negative")
    private double salary;

    @Min(value = 0, message = "Experience from must not be negative")
    private int expFrom;

    @Min(value = 0, message = "Experience to must not be negative")
    private int expTo;

    private boolean isActive;
}