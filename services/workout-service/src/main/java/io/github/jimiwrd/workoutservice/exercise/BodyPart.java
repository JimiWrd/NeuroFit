package io.github.jimiwrd.workoutservice.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BodyPart {
    ARMS("arms"),
    LEGS("legs"),
    BACK("back"),
    CHEST("chest"),
    SHOULDERS("shoulders");

    private final String fieldName;
}
