package io.github.jimiwrd.workoutservice.exercise;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseRep {
    private int num;
    private RepType type;
    private Boolean complete;
}
