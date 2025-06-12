package io.github.jimiwrd.userservice.exception;

import io.github.jimiwrd.userservice.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@SuppressWarnings("unused")
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        return new ResponseEntity<>(ex.toErrorResponse(), ex.getHttpStatus());
    }
}
