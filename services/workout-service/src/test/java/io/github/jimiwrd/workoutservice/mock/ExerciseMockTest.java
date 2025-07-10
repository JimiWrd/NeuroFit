package io.github.jimiwrd.workoutservice.mock;

import io.github.jimiwrd.workoutservice.ExerciseFixtures;
import io.github.jimiwrd.workoutservice.error.ErrorCode;
import io.github.jimiwrd.workoutservice.error.ErrorResponse;
import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseUpdateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ExerciseMockTest extends BaseMockTest {

    @Test
    void createExercise_whenValidRequest_shouldReturnResponse() {
        ExerciseCreateRequest request = ExerciseFixtures.exerciseCreateRequest(BodyPart.ARMS);

        ExerciseResponse response = (ExerciseResponse) createExercise(request, 200);

        assertThat(response.name()).isEqualTo("name");
        assertThat(response.description()).isEqualTo("desc");
        assertThat(response.bodyPart()).isEqualTo(BodyPart.ARMS);
    }

    @Test
    void createExercise_whenInvalidRequest_shouldReturnErrorResponse() {
        ExerciseCreateRequest request = new ExerciseCreateRequest(null, null, null);

        ErrorResponse response = (ErrorResponse) createExercise(request, 400);

        assertThat(response.code()).isEqualTo(ErrorCode.VALIDATION_ERROR);
        assertThat(response.errors().size()).isEqualTo(2);
    }

    @Test
    void getExercise_exerciseExists_shouldReturnResponse() {
        ExerciseCreateRequest request = ExerciseFixtures.exerciseCreateRequest(BodyPart.ARMS);

        ExerciseResponse created = (ExerciseResponse) createExercise(request, 200);

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

    @Test
    void updateExercise_validRequest_shouldReturnResponse() {
        ExerciseCreateRequest createRequest = ExerciseFixtures.exerciseCreateRequest(BodyPart.ARMS);
        ExerciseResponse created = (ExerciseResponse) createExercise(createRequest, 200);

        ExerciseUpdateRequest updateRequest = ExerciseFixtures.exerciseUpdateRequest("new name", "new desc", BodyPart.LEGS);
        ExerciseResponse response = (ExerciseResponse) updateExercise(created.id(), updateRequest, 200);

        assertThat(response.id()).isEqualTo(created.id());
        assertThat(response.name()).isEqualTo("new name");
        assertThat(response.description()).isEqualTo("new desc");
        assertThat(response.bodyPart()).isEqualTo(BodyPart.LEGS);
    }

    @Test
    void updateExercise_doesNotExist_shouldReturnErrorResponse() {
        ExerciseUpdateRequest updateRequest = ExerciseFixtures.exerciseUpdateRequest("new name", "new desc", BodyPart.LEGS);
        ErrorResponse response = (ErrorResponse) updateExercise(UUID.randomUUID(), updateRequest, 400);

        assertThat(response.code()).isEqualTo(ErrorCode.BAD_REQUEST);
    }

    @Test
    void deleteExercise_exists_shouldReturnResponseEntityString() {
        ExerciseCreateRequest request = ExerciseFixtures.exerciseCreateRequest(BodyPart.ARMS);

        ExerciseResponse response = (ExerciseResponse) createExercise(request, 200);

        assertThat(response.name()).isEqualTo("name");
        assertThat(response.description()).isEqualTo("desc");
        assertThat(response.bodyPart()).isEqualTo(BodyPart.ARMS);

        String result = (String) deleteExercise(response.id(), 200);

        assertThat(result).contains("Entity with id: " + response.id() + " deleted.");
    }

    @Test
    void deleteExercise_doesNotExist_shouldReturnErrorResponse() {
        ErrorResponse result = (ErrorResponse) deleteExercise(UUID.randomUUID(), 400);

        assertThat(result.code()).isEqualTo(ErrorCode.BAD_REQUEST);
    }

}
