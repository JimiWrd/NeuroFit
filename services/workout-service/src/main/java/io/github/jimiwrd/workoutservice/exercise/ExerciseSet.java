package io.github.jimiwrd.workoutservice.exercise;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseSet {
    private int num;
    private Map<Integer, ExerciseRep> reps;
    private Boolean complete;
}
