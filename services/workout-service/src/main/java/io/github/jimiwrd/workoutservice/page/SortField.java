package io.github.jimiwrd.workoutservice.page;

import lombok.Getter;

@Getter
public enum SortField {
    // Exercise
    ID("id"),
    NAME("name"),
    DESC("description"),
    BODYPART("bodyPart");

    private final String fieldName;


    SortField(String fieldName) {
        this.fieldName = fieldName;
    }
}
