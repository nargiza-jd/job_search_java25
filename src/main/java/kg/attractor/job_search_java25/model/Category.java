package kg.attractor.job_search_java25.model;

import lombok.Data;

@Data
public class Category {
    private int id;
    private String name;
    private Integer parentId;
}
