package io.github.jimiwrd.workoutservice.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BodyPart {
    ARMS("Arms"),
    LEGS("Legs"),
    BACK("Back"),
    CHEST("Chest"),
    SHOULDERS("Shoulders");

    private final String fieldName;

    public static BodyPart fromFieldName(String fieldName) {
        for (BodyPart type : values()) {
            if (type.fieldName.equalsIgnoreCase(fieldName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with fieldName: " + fieldName);
    }

}
