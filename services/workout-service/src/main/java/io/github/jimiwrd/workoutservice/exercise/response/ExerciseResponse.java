package io.github.jimiwrd.workoutservice.exercise.response;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.ExerciseType;

import java.util.UUID;

public record ExerciseResponse(UUID id,
                               String name,
                               String description,
                               BodyPart bodyPart,
                               ExerciseType exerciseType) {
}
