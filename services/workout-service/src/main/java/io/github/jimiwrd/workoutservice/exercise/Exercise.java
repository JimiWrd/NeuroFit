package io.github.jimiwrd.workoutservice.exercise;

import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseUpdateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseResponse;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;
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

    public ExerciseResponse toResponse() {
        return new ExerciseResponse(
                this.id,
                this.name,
                this.description,
                this.bodyPart
        );
    }

    public boolean merge(ExerciseUpdateRequest request) {
        var dirty = false;

        if(request.name() != null && !Objects.equals(this.name, request.name())) {
            this.name = request.name();
            dirty = true;
        }

        if(request.description() != null && !Objects.equals(this.description, request.description())) {
            this.description = request.description();
            dirty = true;
        }

        if(request.bodyPart() != null && !Objects.equals(this.bodyPart, request.bodyPart())) {
            this.bodyPart = request.bodyPart();
            dirty = true;
        }

        return dirty;
    }

}
