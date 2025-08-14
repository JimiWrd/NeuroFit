package io.github.jimiwrd.workoutservice.exercise.model.request;

import io.github.jimiwrd.workoutservice.exercise.model.constant.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.model.constant.ExerciseType;

public record ExerciseUpdateRequest(String name,
                                    String description,
                                    BodyPart bodyPart,
                                    ExerciseType exerciseType) {
}
