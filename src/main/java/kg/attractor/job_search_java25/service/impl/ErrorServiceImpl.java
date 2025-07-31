package kg.attractor.job_search_java25.service.impl;

import kg.attractor.job_search_java25.exceptions.ErrorResponseBody;
import kg.attractor.job_search_java25.service.ErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

@Service
@Slf4j
public class ErrorServiceImpl implements ErrorService {

    @Override
    public ErrorResponseBody makeResponse(BindingResult bindingResult) {
        Map<String, List<String>> errors = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors
                    .computeIfAbsent(fieldError.getField(), f -> new ArrayList<>())
                    .add(fieldError.getDefaultMessage());
        }

        return ErrorResponseBody.builder()
                .title("Validation error")
                .response(errors)
                .build();
    }
}