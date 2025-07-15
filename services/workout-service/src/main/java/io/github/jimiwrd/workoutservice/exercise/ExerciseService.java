package io.github.jimiwrd.workoutservice.exercise;

import io.github.jimiwrd.workoutservice.error.BadRequestException;
import io.github.jimiwrd.workoutservice.exercise.repository.ExerciseRepository;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseUpdateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseResponse;
import io.github.jimiwrd.workoutservice.page.PageResponse;
import io.github.jimiwrd.workoutservice.page.SortField;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
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

    public PageResponse<ExerciseResponse> getAll(int pageNum, int size, Sort.Direction sort, SortField sortField, Map<String, Object> query) {
        Pageable pageable = PageRequest.of(pageNum, size, sort, sortField.getFieldName());

        if(query != null || !query.isEmpty()) {
            Page<ExerciseResponse> filteredPage =  exerciseRepository.findWithFilters(query, pageable).map(Exercise::toResponse);
            return PageResponse.toResponse(filteredPage);
        }

        Page<ExerciseResponse> page = exerciseRepository.findAll(pageable).map(Exercise::toResponse);

        return PageResponse.toResponse(page);
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
