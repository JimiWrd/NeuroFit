package io.github.jimiwrd.workoutservice.mock;

import io.github.jimiwrd.workoutservice.ExerciseFixtures;
import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseCreateResponse;
import org.junit.jupiter.api.Test;

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
}
