package io.github.jimiwrd.workoutservice.exercise.request;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;

public record ExerciseCreateRequest(String name,
                                    String description,
                                    BodyPart bodyPart) {
}
