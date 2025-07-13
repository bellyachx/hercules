package me.maxhub.hercules.service.exercise.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.maxhub.hercules.entity.exercise.DifficultyEntity;
import me.maxhub.hercules.repo.exercise.DifficultyRepository;
import me.maxhub.hercules.service.exercise.DifficultyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DifficultyServiceImpl implements DifficultyService {

    private final DifficultyRepository difficultyRepository;

    @Override
    public void createDifficulty(String difficultyName) {
        var difficultyEntity = new DifficultyEntity(difficultyName);
        difficultyRepository.save(difficultyEntity);
    }

    @Override
    public void deleteDifficulty(String id) {
        difficultyRepository.deleteById(id);
    }

    @Override
    public void deleteDifficultyByName(String difficultyName) {
        difficultyRepository.deleteByDifficultyName(difficultyName);
    }

    @Override
    public List<String> getDifficulties() {
        return difficultyRepository.findAll().stream()
            .map(DifficultyEntity::getDifficultyName)
            .collect(Collectors.toList());
    }
}
