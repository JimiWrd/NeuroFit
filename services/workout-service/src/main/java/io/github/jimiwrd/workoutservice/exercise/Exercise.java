package io.github.jimiwrd.workoutservice.exercise;

import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseCreateResponse;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("exercise")
public class Exercise {
    private UUID id;
    private String name;
    private String description;
    private BodyPart bodyPart;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public static Exercise from(ExerciseCreateRequest request) {
        return Exercise.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .description(request.description())
                .bodyPart(request.bodyPart())
                .build();
    }

    public ExerciseCreateResponse toCreateResponse() {
        return new ExerciseCreateResponse(
                this.name,
                this.description,
                this.bodyPart
        );
    }

    public ExerciseResponse toResponse() {
        return new ExerciseResponse(
                this.name,
                this.description,
                this.bodyPart
        );
    }

}
