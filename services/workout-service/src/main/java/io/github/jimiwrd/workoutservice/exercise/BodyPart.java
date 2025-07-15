package io.github.jimiwrd.workoutservice.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BodyPart {
    ARMS("ARMS"),
    LEGS("LEGS"),
    BACK("BACK"),
    CHEST("CHEST"),
    SHOULDERS("SHOULDERS");

    private final String fieldName;
}
