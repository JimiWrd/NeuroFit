package io.github.jimiwrd.workoutservice.exercise;

import io.github.jimiwrd.workoutservice.error.BadRequestException;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseCreateResponse;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Transactional
    public ExerciseCreateResponse create(ExerciseCreateRequest request) {
        Exercise result =  exerciseRepository.save(Exercise.from(request));

        return result.toCreateResponse();
    }

    public ExerciseResponse getById(UUID uuid) {
        Optional<Exercise> exercise =  exerciseRepository.findById(uuid);

        return exercise.orElseThrow(() -> new BadRequestException("No Exercise found with ID: " + uuid)).toResponse();
    }
}
