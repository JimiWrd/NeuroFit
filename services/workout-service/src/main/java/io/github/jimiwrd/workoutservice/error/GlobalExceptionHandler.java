package io.github.jimiwrd.workoutservice.error;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        return new ResponseEntity<>(ex.toErrorResponse(), ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = mapErrors(ex.getFieldErrors());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.VALIDATION_ERROR, ex.getBody().getDetail(), errors);
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    private List<String> mapErrors(List<FieldError> fieldErrors) {
        return fieldErrors
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }

}
