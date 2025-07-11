package io.github.jimiwrd.workoutservice.exercise.request;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ExerciseCreateRequest(@NotEmpty(message = "Name should not be empty.")
                                    String name,
                                    String description,
                                    @NotNull(message = "Body Part should not be null.")
                                    BodyPart bodyPart) {
}
