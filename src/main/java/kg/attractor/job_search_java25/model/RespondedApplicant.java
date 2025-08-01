package kg.attractor.job_search_java25.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RespondedApplicant {
    private int id;
    private int resumeId;
    private int vacancyId;
    private int applicantId;
    private boolean confirmation;
    private LocalDateTime responseDate;
}
