package io.github.jimiwrd.userservice.exception;

import io.github.jimiwrd.userservice.error.ErrorCode;
import io.github.jimiwrd.userservice.error.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final ErrorCode errorCode;

    public BaseException(String message, HttpStatus httpStatus, ErrorCode errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public ErrorResponse toErrorResponse() {
        return new ErrorResponse(getMessage(), errorCode);
    }
}
