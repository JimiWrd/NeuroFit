package io.github.jimiwrd.userservice.exception;

import io.github.jimiwrd.userservice.error.ErrorCode;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, ErrorCode.BAD_REQUEST);
    }
}
