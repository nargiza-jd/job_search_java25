package kg.attractor.job_search_java25.dto;

import lombok.Data;

@Data
public class ContactsInfoCreateUpdateDto {
    private Integer id;
    private int typeId;
    private String value;
}