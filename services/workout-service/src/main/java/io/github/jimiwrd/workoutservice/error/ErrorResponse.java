package io.github.jimiwrd.workoutservice.error;

public record ErrorResponse(ErrorCode code,
                            String message) {
}
