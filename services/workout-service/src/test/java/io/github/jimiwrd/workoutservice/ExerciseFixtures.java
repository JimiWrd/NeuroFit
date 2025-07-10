package io.github.jimiwrd.workoutservice;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseUpdateRequest;

public class ExerciseFixtures {

    public static ExerciseCreateRequest exerciseCreateRequest(BodyPart bodyPart) {
        return new ExerciseCreateRequest("name", "desc", bodyPart);
    }

    public static ExerciseUpdateRequest exerciseUpdateRequest(String name, String description, BodyPart bodyPart) {
        return new ExerciseUpdateRequest(name, description, bodyPart);
    }
}
