package io.github.jimiwrd.workoutservice.mock;

import io.github.jimiwrd.workoutservice.ExerciseFixtures;
import io.github.jimiwrd.workoutservice.error.ErrorCode;
import io.github.jimiwrd.workoutservice.error.ErrorResponse;
import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseCreateResponse;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ExerciseMockTest extends BaseMockTest {

    @Test
    void createExercise_whenValidRequest_shouldReturnResponse() {
        ExerciseCreateRequest request = ExerciseFixtures.exerciseCreateRequest(BodyPart.ARMS);

        ExerciseCreateResponse response = (ExerciseCreateResponse) createExercise(request, 200);

        assertThat(response.name()).isEqualTo("name");
        assertThat(response.description()).isEqualTo("desc");
        assertThat(response.bodyPart()).isEqualTo(BodyPart.ARMS);
    }

    @Test
    void createExercise_whenInvalidRequest_shouldReturnErrorResponse() {
        ExerciseCreateRequest request = new ExerciseCreateRequest(null, null, null);

        ErrorResponse response = (ErrorResponse) createExercise(request, 400);

        assertThat(response.code()).isEqualTo(ErrorCode.VALIDATION_ERROR);
    }

    @Test
    void getExercise_exerciseExists_shouldReturnResponse() {
        ExerciseCreateRequest request = ExerciseFixtures.exerciseCreateRequest(BodyPart.ARMS);

        ExerciseCreateResponse created = (ExerciseCreateResponse) createExercise(request, 200);

        ExerciseResponse response = (ExerciseResponse) getExercise(created.id(), 200);

        assertThat(response.id()).isEqualTo(created.id());
        assertThat(response.name()).isEqualTo(created.name());
        assertThat(response.description()).isEqualTo(created.description());
        assertThat(response.bodyPart()).isEqualTo(created.bodyPart());
    }

    @Test
    void getExercise_whenDoesNotExist_shouldReturnErrorResponse() {
        ErrorResponse response = (ErrorResponse) getExercise(UUID.randomUUID(), 400);

        assertThat(response.code()).isEqualTo(ErrorCode.BAD_REQUEST);
    }
}
