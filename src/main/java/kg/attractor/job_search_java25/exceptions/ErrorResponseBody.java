package kg.attractor.job_search_java25.exceptions;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ErrorResponseBody {
    private String title;
    private Map<String, List<String>> response;
}