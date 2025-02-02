package me.maxhub.hercules.controller.exercise;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/exercise/difficulties")
public class DifficultyController {
    // todo implement later

    @PostMapping
    public void createDifficulty(@RequestParam("difficulty") String difficulty) {

    }

    @DeleteMapping("{id}")
    public void deleteDifficulty(@PathVariable("id") String id) {

    }

    @GetMapping
    public List<String> getDifficulties() {
        return null;
    }
}
