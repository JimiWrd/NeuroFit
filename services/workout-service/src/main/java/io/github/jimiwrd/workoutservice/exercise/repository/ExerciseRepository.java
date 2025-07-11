package io.github.jimiwrd.workoutservice.exercise.repository;

import io.github.jimiwrd.workoutservice.exercise.Exercise;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExerciseRepository extends MongoRepository<Exercise, UUID>, ExerciseSpecificationRepository {
}
