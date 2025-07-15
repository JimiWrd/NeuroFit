package io.github.jimiwrd.workoutservice.exercise;

import io.github.jimiwrd.workoutservice.exercise.request.ExerciseCreateRequest;
import io.github.jimiwrd.workoutservice.exercise.request.ExerciseUpdateRequest;
import io.github.jimiwrd.workoutservice.exercise.response.ExerciseResponse;
import io.github.jimiwrd.workoutservice.page.PageResponse;
import io.github.jimiwrd.workoutservice.page.SortField;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
    public ExerciseResponse getById(@PathVariable UUID id) {
        return exerciseService.getById(id);
    }

    @GetMapping("/all")
    public PageResponse<ExerciseResponse> getAll(@RequestParam(defaultValue = "0") int pageNum,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "ASC") Sort.Direction sort,
                                                 @RequestParam(defaultValue = "ID") SortField sortField,
                                                 @RequestParam(defaultValue = "") String name,
                                                 @RequestParam(defaultValue = "") String bodyPart) {
        Map<String, Object> query = Map.of("name", name, "bodyPart", !bodyPart.isBlank() ? BodyPart.valueOf(bodyPart) : bodyPart);

        return exerciseService.getAll(pageNum, size, sort, sortField, query);
    }

    @PutMapping("/{id}")
    public ExerciseResponse update(@PathVariable UUID id, @RequestBody ExerciseUpdateRequest request) {
        return exerciseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        return exerciseService.delete(id);
    }
}
