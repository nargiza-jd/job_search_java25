package kg.attractor.job_search_java25.model;

import lombok.Data;

@Data
class WorkExperienceInfo {
    private int id;
    private int resumeId;
    private int years;
    private String companyName;
    private String position;
    private String responsibilities;
}
