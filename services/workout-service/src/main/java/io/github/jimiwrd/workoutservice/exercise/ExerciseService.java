package io.github.jimiwrd.workoutservice.exercise;

import io.github.jimiwrd.workoutservice.error.BadRequestException;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseUpdateRequest;
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
    public ExerciseResponse create(ExerciseCreateRequest request) {
        Exercise result =  exerciseRepository.save(Exercise.from(request));

        return result.toResponse();
    }

    public ExerciseResponse getById(UUID id) {
        Optional<Exercise> exercise =  exerciseRepository.findById(id);

        return exercise.orElseThrow(() -> new BadRequestException("No Exercise found with ID: " + id)).toResponse();
    }

    @Transactional
    public ExerciseResponse update(UUID id, ExerciseUpdateRequest request) {
        Optional<Exercise> optional = exerciseRepository.findById(id);

        if(optional.isEmpty()) {
            throw new BadRequestException("No Exercise found with ID: " + id);
        }

        Exercise exercise = optional.get();
        boolean dirty = exercise.merge(request);

        if(dirty) {
            exerciseRepository.save(exercise);
        }

        return exercise.toResponse();
    }
}
