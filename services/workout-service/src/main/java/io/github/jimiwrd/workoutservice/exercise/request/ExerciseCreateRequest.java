package io.github.jimiwrd.workoutservice.exercise.request;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import jakarta.validation.constraints.NotNull;

public record ExerciseCreateRequest(@NotNull String name,
                                    @NotNull String description,
                                    @NotNull BodyPart bodyPart) {
}
