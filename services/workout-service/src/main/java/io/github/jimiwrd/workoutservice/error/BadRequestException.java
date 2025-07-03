package io.github.jimiwrd.workoutservice.error;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message, ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }
}
