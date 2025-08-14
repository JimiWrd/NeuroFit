package io.github.jimiwrd.workoutservice.exercise.model.request;

import io.github.jimiwrd.workoutservice.exercise.model.Exercise;
import io.github.jimiwrd.workoutservice.exercise.model.constant.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.model.constant.ExerciseType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ExerciseCreateRequest(@NotEmpty(message = "Name should not be empty.")
                                    String name,
                                    String description,
                                    @NotNull(message = "Body Part should not be null.")
                                    BodyPart bodyPart,
                                    @NotNull(message = "Exercise Type should not be null.")
                                    ExerciseType exerciseType,
                                    Exercise.WeightUnit weightUnit
) {
}
