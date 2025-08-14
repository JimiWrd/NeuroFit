package io.github.jimiwrd.workoutservice.exercise.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SetType {
    DEFAULT("Default"),
    WARM_UP("Warm up"),
    DROP_SET("Drop set"),
    SUPERSET("Superset");

    private final String fieldName;
}
