package io.github.jimiwrd.workoutservice;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;

public class ExerciseFixtures {

    public static ExerciseCreateRequest exerciseCreateRequest(BodyPart bodyPart) {
        return new ExerciseCreateRequest("name", "desc", bodyPart);
    }
}
