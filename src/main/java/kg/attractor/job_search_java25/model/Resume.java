package kg.attractor.job_search_java25.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
class Resume {
    private int id;
    private int applicantId;
    private String name;
    private int categoryId;
    private double salary;
    private boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
