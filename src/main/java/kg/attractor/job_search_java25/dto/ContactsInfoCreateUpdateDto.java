package kg.attractor.job_search_java25.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactsInfoCreateUpdateDto {

    private Integer id;

    @Min(value = 1, message = "Type ID must be a positive number")
    private int typeId;

    @NotBlank(message = "Value cannot be blank")
    @Size(max = 100, message = "Value must be at most 100 characters")
    private String value;
}