package io.github.jimiwrd.workoutservice.exercise.response;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;

public record ExerciseResponse(String name,
                               String description,
                               BodyPart bodyPart) {
}
