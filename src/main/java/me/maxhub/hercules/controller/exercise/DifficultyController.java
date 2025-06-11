package me.maxhub.hercules.controller.exercise;

import lombok.RequiredArgsConstructor;
import me.maxhub.hercules.service.exercise.DifficultyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/exercises/difficulties")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DifficultyController {

    private final DifficultyService service;

    @PostMapping
    public void createDifficulty(@RequestParam("difficulty") String difficulty) {
        service.createDifficulty(difficulty);
    }

    @DeleteMapping("{id}")
    public void deleteDifficulty(@PathVariable("id") String id) {
        service.deleteDifficulty(id);
    }

    @DeleteMapping
    public void deleteExerciseTypeByName(@RequestParam("difficulty_name") String difficultyName) {
        service.deleteDifficultyByName(difficultyName);
    }

    @GetMapping
    public List<String> getDifficulties() {
        return service.getDifficulties();
    }
}
