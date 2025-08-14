package io.github.jimiwrd.workoutservice.exercise.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExerciseType {
    BARBELL("Barbell"),
    DUMBBELL("Dumbbell"),
    KETTLEBELL("Kettlebell"),
    MACHINE("Machine"),
    BODYWEIGHT("Body weight");

    private final String fieldName;

    public static ExerciseType fromFieldName(String fieldName) {
        for (ExerciseType type : values()) {
            if (type.fieldName.equalsIgnoreCase(fieldName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with fieldName: " + fieldName);
    }
}
