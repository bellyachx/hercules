package me.maxhub.hercules.service.exercise;

import java.util.List;

public interface ExerciseTypeService {

    void createExerciseType(String type);

    void deleteExerciseType(String id);

    void deleteExerciseTypeByName(String type);

    List<String> getExerciseTypes();
}
