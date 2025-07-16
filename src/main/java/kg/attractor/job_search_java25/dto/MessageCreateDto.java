package kg.attractor.job_search_java25.dto;

import lombok.Data;

@Data
public class MessageCreateDto {
    private String content;
    private int senderId;
    private int receiverId;
}