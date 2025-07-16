package kg.attractor.job_search_java25.dto;

import lombok.Data;
import java.util.List;

@Data
public class ResumeCreateDto {
    private String name;
    private int categoryId;
    private double salary;
    private int applicantId;
}