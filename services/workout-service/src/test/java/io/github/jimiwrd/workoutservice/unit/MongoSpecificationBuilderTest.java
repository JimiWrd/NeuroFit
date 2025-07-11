package io.github.jimiwrd.workoutservice.unit;

import io.github.jimiwrd.workoutservice.exercise.BodyPart;
import io.github.jimiwrd.workoutservice.utils.MongoSpecificationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MongoSpecificationBuilderTest {
    private MongoSpecificationBuilder specificationBuilder;

    @BeforeEach
    void setUp() {
        specificationBuilder = new MongoSpecificationBuilder();
    }

    @Test
    void should_createEmptyCriteria_whenNoFilters() {
        Map<String, Object> filters = new HashMap<>();

        Criteria result = specificationBuilder.buildCriteria(filters);

        assertThat(result).isNotNull();
        assertThat(result.getCriteriaObject()).isEmpty();
    }

    @Test
    void should_createRegexCriteria_whenNonBlankStrings() {
        Map<String, Object> filters = Map.of(
                "name", "Bicep Curls",
                "createdDate", "2025-06-01"
        );

        Criteria result = specificationBuilder.buildCriteria(filters);

        assertThat(result).isNotNull();
        var criteriaObject = result.getCriteriaObject();

        assertThat(criteriaObject.containsKey("name")).isTrue();
        assertThat(criteriaObject.containsKey("createdDate")).isTrue();

        var nameRegex = criteriaObject.get("name");
        assertThat(nameRegex).isNotNull();
        assertThat(nameRegex.toString()).contains(".*\\QBicep Curls\\E.*"); // \Q and \E are the escape characters used by Pattern.quote()

        var descriptionRegex = criteriaObject.get("createdDate");
        assertThat(descriptionRegex).isNotNull();
        assertThat(descriptionRegex.toString()).contains(".*\\Q2025-06-01\\E.*");
    }

    @Test
    void should_handleMixedDataTypesCorrectly() {
        Map<String, Object> filters = new HashMap<>();
        UUID id = UUID.randomUUID();
        filters.put("name", "Bench Press");
        filters.put("id", id);
        filters.put("description", "A pressing movement using a flat bar on a supine bench.");
        filters.put("bodyPart", BodyPart.ARMS);
        filters.put("boolean", true);
        filters.put("long", 123L);

        Criteria result = specificationBuilder.buildCriteria(filters);

        assertThat(result).isNotNull();
        var criteriaObject = result.getCriteriaObject();

        assertThat(criteriaObject.size()).isEqualTo(6);

        assertThat(criteriaObject.get("name").toString()).contains(".*\\QBench Press\\E.*");

        assertThat(criteriaObject.get("id")).isEqualTo(id);
        assertThat(criteriaObject.get("description").toString()).isEqualTo(".*\\QA pressing movement using a flat bar on a supine bench.\\E.*");
        assertThat(criteriaObject.get("bodyPart")).isEqualTo(BodyPart.ARMS);
        assertThat(criteriaObject.get("boolean")).isEqualTo(true);
        assertThat(criteriaObject.get("long")).isEqualTo(123L);
    }
}
