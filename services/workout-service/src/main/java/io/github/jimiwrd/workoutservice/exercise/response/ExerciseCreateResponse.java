package io.github.jimiwrd.workoutservice.exercise.response;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;

public record ExerciseCreateResponse(String name,
                                     String description,
                                     BodyPart bodyPart) {
}
