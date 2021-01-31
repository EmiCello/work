package user.controller;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import user.HttpException;
import user.ValidationErrorResponse;

import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class WorkRestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(MongoWriteException.class)
    protected ResponseEntity<ValidationErrorResponse> handleEntityAlreadyExistsInDB(MongoWriteException ex) {
        String errorName = ex.getError().getCategory().name();
        if (ErrorCategory.DUPLICATE_KEY.name().equals(errorName)) {
            errorName = "Document already exists. Check fields which should be unique.";
        }
        return new ResponseEntity<>(ValidationErrorResponse.builder()
            .title(errorName)
            .build(),
            UNPROCESSABLE_ENTITY);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        Map<String, String> body = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(FieldError::getField, fe -> {
                    String defaultMessage = fe.getDefaultMessage();
                    return Objects.requireNonNullElse(defaultMessage, "");
                },
                (message1, message2) -> message1));
        return new ResponseEntity<>(ValidationErrorResponse.builder()
            .title("Invalid fields detected.")
            .errors(body)
            .build(),
            headers,
            UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex,
                                                            WebRequest request) {

        Map<String, String> body = ex.getConstraintViolations().stream()
            .collect(Collectors.toMap(e -> e.getInvalidValue().toString(), e -> e.getMessage()));

        return new ResponseEntity<>(ValidationErrorResponse.builder()
            .title("Validation error")
            .errors(body)
            .build(),
            BAD_REQUEST);
    }

    @ExceptionHandler({HttpException.class})
    public ResponseEntity<String> handlePacttHttpException(HttpException ex) {
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.valueOf(ex.getHttpStatus()));
    }
}
