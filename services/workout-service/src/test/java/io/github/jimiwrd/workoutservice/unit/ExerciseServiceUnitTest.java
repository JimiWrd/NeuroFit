package io.github.jimiwrd.workoutservice.unit;

import io.github.jimiwrd.workoutservice.ExerciseFixtures;
import io.github.jimiwrd.workoutservice.error.BadRequestException;
import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.Exercise;
import io.github.jimiwrd.workoutservice.exercise.ExerciseService;
import io.github.jimiwrd.workoutservice.exercise.repository.ExerciseRepository;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

        ExerciseResponse response = exerciseService.create(request);

        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.description()).isEqualTo(request.description());
        assertThat(response.bodyPart()).isEqualTo(request.bodyPart());
    }

    @Test
    void should_getExerciseAndReturnResponse_whenExists() {

        ExerciseCreateRequest request = ExerciseFixtures.exerciseCreateRequest(BodyPart.ARMS);
        Exercise exercise = Exercise.from(request);

        when(exerciseRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(exercise));

        ExerciseResponse response = exerciseService.getById(UUID.randomUUID());

        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.description()).isEqualTo(request.description());
        assertThat(response.bodyPart()).isEqualTo(request.bodyPart());
    }

    @Test
    void should_throwNoSuchElementException_ifExerciseNotFound() {
        assertThatThrownBy(() -> exerciseService.getById(UUID.randomUUID()))
                .isInstanceOf(BadRequestException.class);
    }

}
