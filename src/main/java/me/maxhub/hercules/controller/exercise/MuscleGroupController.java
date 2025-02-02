package me.maxhub.hercules.controller.exercise;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/exercise/muscle-groups")
public class MuscleGroupController {
    // todo implement later

    @PostMapping
    public void createExerciseType(@RequestParam("type") String type) {

    }

    @DeleteMapping("{id}")
    public void deleteExerciseType(@PathVariable("id") String id) {

    }

    @GetMapping
    public List<String> getExerciseTypes() {
        return null;
    }
}
