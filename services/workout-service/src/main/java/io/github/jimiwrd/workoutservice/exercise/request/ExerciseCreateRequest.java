package io.github.jimiwrd.workoutservice.exercise.request;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.Exercise;
import io.github.jimiwrd.workoutservice.exercise.ExerciseSet;
import io.github.jimiwrd.workoutservice.exercise.ExerciseType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ExerciseCreateRequest(@NotEmpty(message = "Name should not be empty.")
                                    String name,
                                    String description,
                                    @NotNull(message = "Body Part should not be null.")
                                    BodyPart bodyPart,
                                    @NotNull(message = "Exercise Type should not be null.")
                                    ExerciseType exerciseType,
                                    Exercise.WeightUnit weightUnit,
                                    List<ExerciseSet> sets) {
}
