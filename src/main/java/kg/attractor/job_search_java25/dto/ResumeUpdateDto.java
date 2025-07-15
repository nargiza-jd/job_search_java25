package kg.attractor.job_search_java25.dto;

import lombok.Data;
import java.util.List;

@Data
public class ResumeUpdateDto {
    private String name;
    private int categoryId;
    private double salary;
    private boolean isActive;
    private List<ContactsInfoCreateUpdateDto> contacts;
    private List<EducationInfoCreateUpdateDto> education;
    private List<WorkExperienceInfoCreateUpdateDto> workExperience;
}