package io.github.jimiwrd.workoutservice;

import io.github.jimiwrd.workoutservice.exercise.model.Exercise;
import io.github.jimiwrd.workoutservice.exercise.model.constant.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.model.constant.ExerciseType;
import io.github.jimiwrd.workoutservice.exercise.model.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.model.request.ExerciseUpdateRequest;
import io.github.jimiwrd.workoutservice.exercise.model.response.ExerciseResponse;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.IntStream;

public class ExerciseFixtures {

    public static ExerciseCreateRequest exerciseCreateRequest() {
        return new ExerciseCreateRequest("name", "desc", BodyPart.ARMS, ExerciseType.BARBELL, Exercise.WeightUnit.KILOGRAMS
        );
    }

    public static ExerciseCreateRequest exerciseCreateRequest(String name, BodyPart bodyPart, ExerciseType exerciseType, Exercise.WeightUnit weightUnit) {
        return new ExerciseCreateRequest(name, "desc", bodyPart, exerciseType, weightUnit);
    }

    public static ExerciseUpdateRequest exerciseUpdateRequest(String name, String description, BodyPart bodyPart, ExerciseType exerciseType) {
        return new ExerciseUpdateRequest(name, description, bodyPart, exerciseType);
    }

    public static List<UUID> createExercises(int num, Function<ExerciseCreateRequest, Object> createExercise) {
        return IntStream.range(0, num).mapToObj(n -> {

            ExerciseCreateRequest request = exerciseCreateRequest("name" + n, BodyPart.ARMS, ExerciseType.BARBELL, Exercise.WeightUnit.KILOGRAMS);
            return  (ExerciseResponse) createExercise.apply(request);
        }).map(ExerciseResponse::id).toList();
    }

    public static List<UUID> createExercises(int num,
                                             BodyPart bodyPart,
                                             ExerciseType exerciseType,
                                             Exercise.WeightUnit weightUnit,
                                             Function<ExerciseCreateRequest, Object> createExercise) {
        return IntStream.range(0, num).mapToObj(n -> {
            ExerciseCreateRequest request = exerciseCreateRequest("name" + n, bodyPart, exerciseType, weightUnit);
            return  (ExerciseResponse) createExercise.apply(request);
        }).map(ExerciseResponse::id).toList();
    }
}
