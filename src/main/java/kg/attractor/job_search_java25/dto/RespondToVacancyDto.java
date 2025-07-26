package kg.attractor.job_search_java25.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RespondToVacancyDto {

    @Min(value = 1, message = "Resume ID must be a positive number")
    private int resumeId;

    @Min(value = 1, message = "Vacancy ID must be a positive number")
    private int vacancyId;
}