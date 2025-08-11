package io.github.jimiwrd.workoutservice.exercise.request;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.ExerciseType;

public record ExerciseUpdateRequest(String name,
                                    String description,
                                    BodyPart bodyPart,
                                    ExerciseType exerciseType) {
}
