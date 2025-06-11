package me.maxhub.hercules.service.exercise.impl;

import lombok.RequiredArgsConstructor;
import me.maxhub.hercules.entity.exercise.ExerciseTypeEntity;
import me.maxhub.hercules.repo.exercise.ExerciseTypeRepository;
import me.maxhub.hercules.service.exercise.ExerciseTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseTypeServiceImpl implements ExerciseTypeService {

    private final ExerciseTypeRepository exerciseTypeRepository;

    @Override
    public void createExerciseType(String type) {
        var exerciseTypeEntity = new ExerciseTypeEntity(type);
        exerciseTypeRepository.save(exerciseTypeEntity);
    }

    @Override
    public void deleteExerciseType(String id) {
        exerciseTypeRepository.deleteById(id);
    }

    @Override
    public void deleteExerciseTypeByName(String type) {
        exerciseTypeRepository.deleteByExerciseTypeName(type);
    }

    @Override
    public List<String> getExerciseTypes() {
        return exerciseTypeRepository.findAll().stream()
            .map(ExerciseTypeEntity::getExerciseTypeName)
            .collect(Collectors.toList());
    }
}
