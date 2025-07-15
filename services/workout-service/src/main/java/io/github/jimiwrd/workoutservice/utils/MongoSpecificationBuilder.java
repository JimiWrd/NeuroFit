package io.github.jimiwrd.workoutservice.utils;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class MongoSpecificationBuilder {
    public Criteria buildCriteria(Map<String, Object> filters) {
        Criteria criteria = new Criteria();

        for (var entry : filters.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            switch (v) {
                case null -> {
                    // Skip null
                }
                case String str when !str.isBlank() -> {
                    criteria = criteria.and(k).regex(".*" + Pattern.quote(str) +".*", "i"); // i flag is 'case insensitive'
                }
                case UUID id -> {
                    criteria = criteria.and(k).is(id);
                }
                case Boolean bool -> {
                    criteria = criteria.and(k).is(bool);
                }
                case BodyPart bp -> {
                    criteria = criteria.and(k).is(bp);
                }
                case Number n -> {
                    criteria = criteria.and(k).is(n);
                }
                default -> {}
            }
        }
        return criteria;
    }
}
