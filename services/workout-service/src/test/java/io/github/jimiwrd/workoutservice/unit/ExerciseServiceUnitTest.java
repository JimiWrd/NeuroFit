package io.github.jimiwrd.workoutservice.unit;

import io.github.jimiwrd.workoutservice.ExerciseFixtures;
import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.Exercise;
import io.github.jimiwrd.workoutservice.exercise.ExerciseRepository;
import io.github.jimiwrd.workoutservice.exercise.ExerciseService;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseCreateResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceUnitTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    @Test
    void should_createNewExercise_whenValidCreateResponseGiven() {
        ExerciseCreateRequest request = ExerciseFixtures.exerciseCreateRequest(BodyPart.ARMS);

        when(exerciseRepository.save(ArgumentMatchers.any(Exercise.class))).thenReturn(Exercise.from(request));

        ExerciseCreateResponse response = exerciseService.create(request);

        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.description()).isEqualTo(request.description());
        assertThat(response.bodyPart()).isEqualTo(request.bodyPart());
    }

}
