package io.github.jimiwrd.workoutservice.exercise.model;

import io.github.jimiwrd.workoutservice.exercise.model.constant.BodyPart;
import io.github.jimiwrd.workoutservice.exercise.model.constant.ExerciseType;
import io.github.jimiwrd.workoutservice.exercise.model.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.model.request.ExerciseUpdateRequest;
import io.github.jimiwrd.workoutservice.exercise.model.response.ExerciseResponse;
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
    private ExerciseType type;
    private WeightUnit weightUnit;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Getter
    @AllArgsConstructor
    public enum WeightUnit {
        KILOGRAMS("KG"),
        POUNDS("LBS");

        private final String fieldName;

        public static WeightUnit fromFieldName(String fieldName) {
            for (WeightUnit type : values()) {
                if (type.fieldName.equalsIgnoreCase(fieldName)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant with fieldName: " + fieldName);
        }
    }

    public static Exercise from(ExerciseCreateRequest request) {
        return Exercise.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .description(request.description())
                .bodyPart(request.bodyPart())
                .type(request.exerciseType())
                .weightUnit(request.weightUnit())
                .build();
    }

    public ExerciseResponse toResponse() {
        return new ExerciseResponse(
                this.id,
                this.name,
                this.description,
                this.bodyPart,
                this.type,
                this.weightUnit
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

        if(request.exerciseType() != null && !Objects.equals(this.type, request.exerciseType())) {
            this.type = request.exerciseType();
            dirty = true;
        }

        return dirty;
    }

}
