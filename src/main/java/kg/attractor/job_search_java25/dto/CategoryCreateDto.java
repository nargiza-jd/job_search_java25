package kg.attractor.job_search_java25.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryCreateDto {

    @NotBlank(message = "Название категории не может быть пустым")
    @Size(min = 2, max = 100, message = "Название категории должно быть от 2 до 100 символов")
    private String name;

    private Integer parentId;
}