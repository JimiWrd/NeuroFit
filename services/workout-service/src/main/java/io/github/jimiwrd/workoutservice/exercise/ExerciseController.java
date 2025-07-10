package io.github.jimiwrd.workoutservice.exercise;

import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseUpdateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/exercise")
@AllArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    public ExerciseResponse create(@Valid @RequestBody ExerciseCreateRequest request) {
        return exerciseService.create(request);
    }

    @GetMapping("/{id}")
    public ExerciseResponse getById(@PathVariable("id") UUID id) {
        return exerciseService.getById(id);
    }

    @PutMapping("/{id}")
    public ExerciseResponse update(@PathVariable("id") UUID id, @RequestBody ExerciseUpdateRequest request) {
        return exerciseService.update(id, request);
    }
}
