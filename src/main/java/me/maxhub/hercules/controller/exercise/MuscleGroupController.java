package me.maxhub.hercules.controller.exercise;

import lombok.RequiredArgsConstructor;
import me.maxhub.hercules.service.exercise.MuscleGroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/exercises/muscle-groups")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class MuscleGroupController {

    private final MuscleGroupService service;

    @PostMapping
    public void createExerciseType(@RequestParam("type") String type) {
        service.createMuscleGroup(type);
    }

    @DeleteMapping("{id}")
    public void deleteExerciseType(@PathVariable("id") String id) {
        service.deleteMuscleGroup(id);
    }

    @DeleteMapping
    public void deleteExerciseTypeByGroupName(@RequestParam("group_name") String groupName) {
        service.deleteMuscleGroupByName(groupName);
    }

    @GetMapping
    public List<String> getExerciseTypes() {
        return service.getMuscleGroups();
    }
}
