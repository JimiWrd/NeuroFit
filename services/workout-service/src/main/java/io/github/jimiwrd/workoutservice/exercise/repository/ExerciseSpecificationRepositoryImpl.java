package io.github.jimiwrd.workoutservice.exercise.repository;

import io.github.jimiwrd.workoutservice.exercise.Exercise;
import io.github.jimiwrd.workoutservice.utils.MongoSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ExerciseSpecificationRepositoryImpl implements ExerciseSpecificationRepository {

    private final MongoTemplate mongoTemplate;
    private final MongoSpecificationBuilder specificationBuilder;

    @Override
    public Page<Exercise> findWithFilters(Map<String, Object> filters, Pageable pageable) {
        var criteria = specificationBuilder.buildCriteria(filters);
        var query = Query.query(criteria).with(pageable);

        var exercises = mongoTemplate.find(query, Exercise.class);
        var total = mongoTemplate.count(query, Exercise.class);

        return new PageImpl<Exercise>(exercises, pageable, total);
    }
}
