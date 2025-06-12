package io.github.jimiwrd.userservice.error;

public record ErrorResponse(String message, ErrorCode errorCode) {
}
