package io.github.jimiwrd.workoutservice.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleException(BaseException ex) {
        return new ResponseEntity<>(ex.toErrorResponse(), ex.getHttpStatus());
    }

}
