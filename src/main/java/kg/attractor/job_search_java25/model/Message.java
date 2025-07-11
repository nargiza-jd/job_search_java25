package kg.attractor.job_search_java25.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
class Message {
    private int id;
    private int respondedApplicants;
    private String content;
    private LocalDateTime timestamp;
}
