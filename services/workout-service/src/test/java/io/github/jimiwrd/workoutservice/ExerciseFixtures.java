package io.github.jimiwrd.workoutservice;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseUpdateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseResponse;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.IntStream;

public class ExerciseFixtures {

    public static ExerciseCreateRequest exerciseCreateRequest(BodyPart bodyPart) {
        return new ExerciseCreateRequest("name", "desc", bodyPart);
    }

    public static ExerciseCreateRequest exerciseCreateRequest(String name, BodyPart bodyPart) {
        return new ExerciseCreateRequest(name, "desc", bodyPart);
    }

    public static ExerciseUpdateRequest exerciseUpdateRequest(String name, String description, BodyPart bodyPart) {
        return new ExerciseUpdateRequest(name, description, bodyPart);
    }

    public static List<UUID> createExercises(int num, Function<ExerciseCreateRequest, Object> createExercise) {
        return IntStream.range(0, num).mapToObj(n -> {
            ExerciseCreateRequest request = exerciseCreateRequest("name" + n, BodyPart.ARMS);
            return  (ExerciseResponse) createExercise.apply(request);
        }).map(ExerciseResponse::id).toList();
    }
}
