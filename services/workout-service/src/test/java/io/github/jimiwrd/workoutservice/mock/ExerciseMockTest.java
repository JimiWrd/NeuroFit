package io.github.jimiwrd.workoutservice.mock;

import io.github.jimiwrd.workoutservice.ExerciseFixtures;
import io.github.jimiwrd.workoutservice.error.ErrorCode;
import io.github.jimiwrd.workoutservice.error.ErrorResponse;
import io.github.jimiwrd.workoutservice.exercise.model.Exercise;
import io.github.jimiwrd.workoutservice.exercise.model.constant.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.model.constant.ExerciseType;
import io.github.jimiwrd.workoutservice.exercise.model.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.model.request.ExerciseUpdateRequest;
import io.github.jimiwrd.workoutservice.exercise.model.response.ExerciseResponse;
import io.github.jimiwrd.workoutservice.page.PageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ExerciseMockTest extends BaseMockTest {

    @Test
    void createExercise_whenValidRequest_shouldReturnResponse() {
        ExerciseCreateRequest request = ExerciseFixtures.exerciseCreateRequest();

        ExerciseResponse response = (ExerciseResponse) createExercise(request, 200);

        assertThat(response.name()).isEqualTo("name");
        assertThat(response.description()).isEqualTo("desc");
        assertThat(response.bodyPart()).isEqualTo(BodyPart.ARMS);
        assertThat(response.exerciseType()).isEqualTo(ExerciseType.BARBELL);
    }

    @Test
    void createExercise_whenInvalidRequest_shouldReturnErrorResponse() {
        ExerciseCreateRequest request = new ExerciseCreateRequest(null, null, null, null, null);

        ErrorResponse response = (ErrorResponse) createExercise(request, 400);

        assertThat(response.code()).isEqualTo(ErrorCode.VALIDATION_ERROR);
        assertThat(response.errors().size()).isEqualTo(3);
    }

    @Test
    void getExercise_exerciseExists_shouldReturnResponse() {
        ExerciseCreateRequest request = ExerciseFixtures.exerciseCreateRequest();

        ExerciseResponse created = (ExerciseResponse) createExercise(request, 200);

        ExerciseResponse response = (ExerciseResponse) getExercise(created.id(), 200);

        assertThat(response.id()).isEqualTo(created.id());
        assertThat(response.name()).isEqualTo(created.name());
        assertThat(response.description()).isEqualTo(created.description());
        assertThat(response.bodyPart()).isEqualTo(created.bodyPart());
        assertThat(response.exerciseType()).isEqualTo(created.exerciseType());
    }

    @Test
    void getExercise_whenDoesNotExist_shouldReturnErrorResponse() {
        ErrorResponse response = (ErrorResponse) getExercise(UUID.randomUUID(), 400);

        assertThat(response.code()).isEqualTo(ErrorCode.BAD_REQUEST);
    }

    @Test
    void updateExercise_validRequest_shouldReturnResponse() {
        ExerciseCreateRequest createRequest = ExerciseFixtures.exerciseCreateRequest();
        ExerciseResponse created = (ExerciseResponse) createExercise(createRequest, 200);

        ExerciseUpdateRequest updateRequest = ExerciseFixtures.exerciseUpdateRequest("new name", "new desc", BodyPart.LEGS, ExerciseType.DUMBBELL);
        ExerciseResponse response = (ExerciseResponse) updateExercise(created.id(), updateRequest, 200);

        assertThat(response.id()).isEqualTo(created.id());
        assertThat(response.name()).isEqualTo("new name");
        assertThat(response.description()).isEqualTo("new desc");
        assertThat(response.bodyPart()).isEqualTo(BodyPart.LEGS);
        assertThat(response.exerciseType()).isEqualTo(ExerciseType.DUMBBELL);

    }

    @Test
    void updateExercise_doesNotExist_shouldReturnErrorResponse() {
        ExerciseUpdateRequest updateRequest = ExerciseFixtures.exerciseUpdateRequest("new name", "new desc", BodyPart.LEGS, ExerciseType.DUMBBELL);
        ErrorResponse response = (ErrorResponse) updateExercise(UUID.randomUUID(), updateRequest, 400);

        assertThat(response.code()).isEqualTo(ErrorCode.BAD_REQUEST);
    }

    @Test
    void deleteExercise_exists_shouldReturnResponseEntityString() {
        ExerciseCreateRequest request = ExerciseFixtures.exerciseCreateRequest();

        ExerciseResponse response = (ExerciseResponse) createExercise(request, 200);

        assertThat(response.name()).isEqualTo("name");
        assertThat(response.description()).isEqualTo("desc");
        assertThat(response.bodyPart()).isEqualTo(BodyPart.ARMS);
        assertThat(response.exerciseType()).isEqualTo(ExerciseType.BARBELL);

        String result = (String) deleteExercise(response.id(), 200);

        assertThat(result).contains("Entity with id: " + response.id() + " deleted.");
    }

    @Test
    void deleteExercise_doesNotExist_shouldReturnErrorResponse() {
        ErrorResponse result = (ErrorResponse) deleteExercise(UUID.randomUUID(), 400);

        assertThat(result.code()).isEqualTo(ErrorCode.BAD_REQUEST);
    }

    @Test
    void getAllExercises_shouldReturnPageResponses() {
        //Create 10 exercises to fetch
        ExerciseFixtures.createExercises(10, this::createExercise);

        int pageNum = 0;
        int size = 10;

        PageResponse<ExerciseResponse> responsePage = (PageResponse<ExerciseResponse>) getAllExercises(pageNum, size, null, null, null, null, 200);

        assertThat(responsePage.getSize()).isEqualTo(size);
        assertThat(responsePage.getTotalPages()).isEqualTo(1);
        assertThat(responsePage.getContent().getFirst()).isInstanceOf(ExerciseResponse.class);
    }

    @Test
    void getAllExercises_withFilterByNameAndBodyPart_shouldReturnPageResponses_filteredByNameAndBodyPart() {
        //Create 13 exercises to fetch, different BodyParts
        ExerciseFixtures.createExercises(10, this::createExercise);

        ExerciseFixtures.createExercises(3, BodyPart.BACK, ExerciseType.BARBELL, Exercise.WeightUnit.KILOGRAMS, this::createExercise);

        int pageNum = 0;
        int size = 10;
        Sort.Direction sort = Sort.Direction.DESC;

        PageResponse<ExerciseResponse> responsePage = (PageResponse<ExerciseResponse>) getAllExercises(pageNum, size, null,"name1", BodyPart.BACK, ExerciseType.BARBELL, 200);

        assertThat(responsePage.getSize()).isEqualTo(1);
        assertThat(responsePage.getTotalPages()).isEqualTo(1);
        ExerciseResponse content = responsePage.getContent().getFirst();
        assertThat(content).isInstanceOf(ExerciseResponse.class);
        assertThat(content.bodyPart()).isEqualTo(BodyPart.BACK);
        assertThat(content.exerciseType()).isEqualTo(ExerciseType.BARBELL);
        assertThat(content.name()).isEqualTo("name1");
    }

    @Test
    void getAllExercises_withFilterByName_shouldReturnPageResponses_filteredByName() {
        //Create 13 exercises to fetch, different BodyParts
        ExerciseFixtures.createExercises(10, this::createExercise);

        ExerciseFixtures.createExercises(3, BodyPart.BACK, ExerciseType.BARBELL, Exercise.WeightUnit.KILOGRAMS, this::createExercise);

        int pageNum = 0;
        int size = 15;
        Sort.Direction sort = Sort.Direction.DESC;

        PageResponse<ExerciseResponse> responsePage = (PageResponse<ExerciseResponse>) getAllExercises(pageNum, size, null,"name", null, null, 200);

        assertThat(responsePage.getSize()).isEqualTo(13);
        assertThat(responsePage.getTotalPages()).isEqualTo(1);
        ExerciseResponse content = responsePage.getContent().getFirst();
        assertThat(content).isInstanceOf(ExerciseResponse.class);

        responsePage = (PageResponse<ExerciseResponse>) getAllExercises(pageNum, size, null,"name1", null, null, 200);
        assertThat(responsePage.getSize()).isEqualTo(2);
        assertThat(responsePage.getTotalPages()).isEqualTo(1);
        content = responsePage.getContent().getFirst();
        assertThat(content).isInstanceOf(ExerciseResponse.class);
    }

    @Test
    void getAllExercises_withFilterByBodyPart_shouldReturnPageResponses_filteredByBodyPart() {
        //Create 13 exercises to fetch, different BodyParts
        ExerciseFixtures.createExercises(10, this::createExercise);

        ExerciseFixtures.createExercises(3, BodyPart.BACK, ExerciseType.BARBELL, Exercise.WeightUnit.KILOGRAMS, this::createExercise);

        int pageNum = 0;
        int size = 10;
        Sort.Direction sort = Sort.Direction.DESC;

        PageResponse<ExerciseResponse> responsePage = (PageResponse<ExerciseResponse>) getAllExercises(pageNum, size, null,null, BodyPart.ARMS, null, 200);

        assertThat(responsePage.getSize()).isEqualTo(10);
        assertThat(responsePage.getTotalPages()).isEqualTo(1);
        ExerciseResponse content = responsePage.getContent().getFirst();
        assertThat(content).isInstanceOf(ExerciseResponse.class);

        responsePage = (PageResponse<ExerciseResponse>) getAllExercises(pageNum, size, null,null, BodyPart.BACK, null, 200);
        assertThat(responsePage.getSize()).isEqualTo(3);
        assertThat(responsePage.getTotalPages()).isEqualTo(1);
        content = responsePage.getContent().getFirst();
        assertThat(content).isInstanceOf(ExerciseResponse.class);
    }

    @Test
    void getAllExercises_withFilterByExerciseType_shouldReturnPageResponses_filteredByExerciseType() {
        //Create 13 exercises to fetch, different BodyParts
        ExerciseFixtures.createExercises(10, this::createExercise);

        ExerciseFixtures.createExercises(3, BodyPart.BACK, ExerciseType.DUMBBELL, Exercise.WeightUnit.KILOGRAMS, this::createExercise);

        int pageNum = 0;
        int size = 10;
        Sort.Direction sort = Sort.Direction.DESC;

        PageResponse<ExerciseResponse> responsePage = (PageResponse<ExerciseResponse>) getAllExercises(pageNum, size, null,null, null, ExerciseType.BARBELL, 200);

        assertThat(responsePage.getSize()).isEqualTo(10);
        assertThat(responsePage.getTotalPages()).isEqualTo(1);
        ExerciseResponse content = responsePage.getContent().getFirst();
        assertThat(content).isInstanceOf(ExerciseResponse.class);

        responsePage = (PageResponse<ExerciseResponse>) getAllExercises(pageNum, size, null,null, null, ExerciseType.DUMBBELL, 200);
        assertThat(responsePage.getSize()).isEqualTo(3);
        assertThat(responsePage.getTotalPages()).isEqualTo(1);
        content = responsePage.getContent().getFirst();
        assertThat(content).isInstanceOf(ExerciseResponse.class);
    }

}
