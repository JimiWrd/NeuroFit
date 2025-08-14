package io.github.jimiwrd.workoutservice.exercise.model.response;

import io.github.jimiwrd.workoutservice.exercise.model.Exercise;
import io.github.jimiwrd.workoutservice.exercise.model.constant.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.model.constant.ExerciseType;

import java.util.UUID;

public record ExerciseResponse(UUID id,
                               String name,
                               String description,
                               BodyPart bodyPart,
                               ExerciseType exerciseType,
                               Exercise.WeightUnit weightUnit) {
}
