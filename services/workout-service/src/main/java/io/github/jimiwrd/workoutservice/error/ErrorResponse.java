package io.github.jimiwrd.workoutservice.error;

import java.util.List;

public record ErrorResponse(ErrorCode code,
                            String message,
                            List<String> errors) {
}
