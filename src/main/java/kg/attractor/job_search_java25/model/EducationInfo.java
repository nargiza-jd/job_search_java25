package kg.attractor.job_search_java25.model;

import lombok.Data;

import java.time.LocalDate;

@Data
class EducationInfo {
    private int id;
    private int resumeId;
    private String institution;
    private String program;
    private LocalDate startDate;
    private LocalDate endDate;
    private String degree;
}
