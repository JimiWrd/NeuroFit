package io.github.jimiwrd.workoutservice.exercise.request;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ExerciseUpdateRequest(String name,
                                    String description,
                                    BodyPart bodyPart) {
}
