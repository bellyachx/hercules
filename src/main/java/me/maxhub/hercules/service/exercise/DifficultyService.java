package me.maxhub.hercules.service.exercise;

import java.util.List;

public interface DifficultyService {

    void createDifficulty(String difficultyName);

    void deleteDifficulty(String id);

    void deleteDifficultyByName(String difficultyName);

    List<String> getDifficulties();
}
