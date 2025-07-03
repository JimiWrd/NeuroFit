package io.github.jimiwrd.workoutservice.exercise;

import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseCreateResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Transactional
    public ExerciseCreateResponse create(ExerciseCreateRequest request) {
        Exercise result =  exerciseRepository.save(Exercise.from(request));

        return result.toResponse();
    }
}
