package kg.attractor.job_search_java25.exceptions;

import kg.attractor.job_search_java25.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    private final ErrorService errorService;

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        log.info("NotFoundException occurred: {}", ex.getMessage());
        return ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseBody> validationHandler(MethodArgumentNotValidException ex) {
        log.warn("Validation error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(errorService.makeResponse(ex.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseBody> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        log.error("DataIntegrityViolationException occurred: {}", ex.getMessage(), ex);

        String errorMessage = "Ошибка данных: нарушена целостность. Возможно, такой ресурс уже существует.";
        if (ex.getRootCause() != null && ex.getRootCause().getMessage() != null) {
            String rootCauseMessage = ex.getRootCause().getMessage();
            if (rootCauseMessage.contains("Unique index or primary key violation")) {
                errorMessage = "Объект с такими уникальными данными уже существует. Пожалуйста, проверьте вводимые данные.";
            } else if (rootCauseMessage.contains("FOREIGN KEY")) {
                errorMessage = "Нарушена связь с другими данными. Убедитесь, что связанные объекты существуют.";
            }
        }

        ErrorResponseBody errorBody = ErrorResponseBody.builder()
                .title("Data Integrity Error")
                .response(Map.of("error", List.of(errorMessage)))
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseBody> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("IllegalArgumentException occurred: {}", ex.getMessage());
        ErrorResponseBody errorBody = ErrorResponseBody.builder()
                .title("Bad Request")
                .response(Map.of("error", List.of(ex.getMessage())))
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseBody> handleGeneralException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        ErrorResponseBody errorBody = ErrorResponseBody.builder()
                .title("Internal Server Error")
                .response(Map.of("error", List.of("Произошла внутренняя ошибка сервера. Пожалуйста, попробуйте позже.")))
                .build();
        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}