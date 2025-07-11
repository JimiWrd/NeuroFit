package io.github.jimiwrd.workoutservice.exercise.repository;

import io.github.jimiwrd.workoutservice.exercise.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ExerciseSpecificationRepository {
    Page<Exercise> findWithFilters(Map<String, Object> filters, Pageable pageable);
}
