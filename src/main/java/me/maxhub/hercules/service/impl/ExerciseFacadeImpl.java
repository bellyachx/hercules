package me.maxhub.hercules.service.impl;

import lombok.RequiredArgsConstructor;
import me.maxhub.hercules.dto.ExerciseRequestDto;
import me.maxhub.hercules.dto.ExerciseResponseDto;
import me.maxhub.hercules.entity.exercise.*;
import me.maxhub.hercules.exception.ExerciseNotFoundException;
import me.maxhub.hercules.mapper.ExerciseMapper;
import me.maxhub.hercules.repo.DifficultyRepository;
import me.maxhub.hercules.repo.ExerciseRepository;
import me.maxhub.hercules.repo.ExerciseTypeRepository;
import me.maxhub.hercules.repo.MuscleGroupRepository;
import me.maxhub.hercules.service.ExerciseFacade;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExerciseFacadeImpl implements ExerciseFacade {

    private final ExerciseRepository exerciseRepository;
    private final DifficultyRepository difficultyRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final ExerciseMapper mapper;

    @Override
    @Transactional
    public void createExercise(ExerciseRequestDto exerciseRequestDto) {
        exerciseRepository.save(getExerciseEntity(exerciseRequestDto));
    }

    @Override
    @Transactional
    public void updateExercise(String id, ExerciseRequestDto exerciseRequestDto) {
        var exerciseEntity = exerciseRepository.findById(id);
        if (exerciseEntity.isEmpty()) {
            throw new ExerciseNotFoundException(id);
        }

        var entityToSave = getExerciseEntity(exerciseRequestDto);
        entityToSave.setId(id);
        exerciseRepository.save(entityToSave);
    }

    @Override
    public void deleteExercise(String id) {
        if (!exerciseRepository.existsById(id)) {
            throw new ExerciseNotFoundException(id);
        }
        exerciseRepository.deleteById(id);
    }

    @Override
    public ExerciseResponseDto getExercise(String id) {
        return exerciseRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ExerciseNotFoundException(id));
    }

    @Override
    public Collection<ExerciseResponseDto> getExercises() {
        var exercises = exerciseRepository.findAll();
        return exercises.stream().map(mapper::toDto).toList();
    }

    private Collection<ExerciseMuscleGroupEntity> createMuscleGroups(Collection<String> muscleGroups,
                                                                     ExerciseEntity exerciseEntity) {
        var existingGroups = muscleGroupRepository.findAllByGroupNameIn(muscleGroups);
        var existingGroupNames = existingGroups.stream()
                .map(MuscleGroupEntity::getGroupName)
                .collect(Collectors.toSet());

        List<MuscleGroupEntity> newGroups = muscleGroups.stream()
                .filter(group -> !existingGroupNames.contains(group))
                .map(MuscleGroupEntity::new)
                .toList();

        if (!newGroups.isEmpty()) {
            muscleGroupRepository.saveAll(newGroups);
            existingGroups.addAll(newGroups);
        }

        return existingGroups.stream()
                .map(muscleGroupEntity -> {
                    var exerciseMuscleGroupEntity = new ExerciseMuscleGroupEntity();
                    exerciseMuscleGroupEntity.setExercise(exerciseEntity);
                    exerciseMuscleGroupEntity.setMuscleGroup(muscleGroupEntity);
                    return exerciseMuscleGroupEntity;
                })
                .toList();
    }

    private ExerciseEntity getExerciseEntity(ExerciseRequestDto exerciseRequestDto) {
        var difficultyName = exerciseRequestDto.getDifficulty();
        var difficultyEntity = getDifficultyEntity(difficultyName);
        var exerciseTypeEntity = getExerciseTypeEntity(exerciseRequestDto);
        var entity = mapper.toEntity(exerciseRequestDto, difficultyEntity, exerciseTypeEntity);
        entity.setMuscleGroups(createMuscleGroups(exerciseRequestDto.getMuscleGroups(), entity));
        return entity;
    }

    private DifficultyEntity getDifficultyEntity(String difficultyName) {
        return difficultyRepository.findByDifficultyName(difficultyName)
                .orElseGet(() -> difficultyRepository.save(new DifficultyEntity(difficultyName)));
    }

    private ExerciseTypeEntity getExerciseTypeEntity(ExerciseRequestDto exerciseRequestDto) {
        return exerciseTypeRepository.findByExerciseTypeName(exerciseRequestDto.getExerciseType())
                .orElseGet(() -> exerciseTypeRepository.save(new ExerciseTypeEntity(exerciseRequestDto.getExerciseType())));
    }
}
