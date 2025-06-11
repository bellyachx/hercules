package me.maxhub.hercules.controller.exercise;

import lombok.RequiredArgsConstructor;
import me.maxhub.hercules.service.exercise.ExerciseTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/exercises/types")
@RequiredArgsConstructor
@PreAuthorize("hasRole('admin')")
public class ExerciseTypeController {

    private final ExerciseTypeService service;

    @PostMapping
    public void createExerciseType(@RequestParam("type") String type) {
        service.createExerciseType(type);
    }

    @DeleteMapping("{id}")
    public void deleteExerciseType(@PathVariable("id") String id) {
        service.deleteExerciseType(id);
    }

    @DeleteMapping
    public void deleteExerciseTypeByName(@RequestParam("type") String exerciseTypeName) {
        service.deleteExerciseTypeByName(exerciseTypeName);
    }

    @GetMapping
    public List<String> getExerciseTypes() {
        return service.getExerciseTypes();
    }
}
