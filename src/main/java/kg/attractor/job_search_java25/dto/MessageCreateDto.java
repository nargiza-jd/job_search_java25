package kg.attractor.job_search_java25.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageCreateDto {

    @NotBlank(message = "Content must not be blank")
    @Size(max = 1000, message = "Content must be at most 1000 characters")
    private String content;

    @Min(value = 1, message = "Sender ID must be a positive number")
    private int senderId;

    @Min(value = 1, message = "Receiver ID must be a positive number")
    private int receiverId;
}