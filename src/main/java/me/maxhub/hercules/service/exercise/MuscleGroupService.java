package me.maxhub.hercules.service.exercise;

import java.util.List;

public interface MuscleGroupService {

    void createMuscleGroup(String muscleGroupName);

    void deleteMuscleGroup(String id);

    void deleteMuscleGroupByName(String muscleGroupName);

    List<String> getMuscleGroups();
}
