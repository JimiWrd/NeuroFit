package io.github.jimiwrd.workoutservice.exercise;

import io.github.jimiwrd.workoutservice.error.BadRequestException;
import io.github.jimiwrd.workoutservice.exercise.repository.ExerciseRepository;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseUpdateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
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
        var dirty = exercise.merge(request);

        if(dirty) {
            exerciseRepository.save(exercise);
        }

        return exercise.toResponse();
    }

    @Transactional
    public ResponseEntity<String> delete(UUID id) {
        Optional<Exercise> optional = exerciseRepository.findById(id);

        if(optional.isEmpty()) {
            throw new BadRequestException("No Exercise found with ID: " + id);
        }

        exerciseRepository.deleteById(id);

        return ResponseEntity.ok("Entity with id: " + id.toString() + " deleted.");
    }
}
