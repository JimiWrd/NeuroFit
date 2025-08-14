package io.github.jimiwrd.workoutservice.exercise.model;

import io.github.jimiwrd.workoutservice.exercise.model.constant.SetType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutSet {
    //TODO: Implement Workout Class, removing this from Exercise as incorrect domain
    private int num;
    private int reps;
    @Builder.Default
    private SetType type = SetType.DEFAULT;
    private Boolean complete;
}
