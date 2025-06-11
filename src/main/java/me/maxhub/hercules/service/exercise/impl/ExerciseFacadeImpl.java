package me.maxhub.hercules.service.exercise.impl;

import lombok.RequiredArgsConstructor;
import me.maxhub.hercules.dto.ExerciseRequestDto;
import me.maxhub.hercules.dto.ExerciseResponseDto;
import me.maxhub.hercules.entity.exercise.*;
import me.maxhub.hercules.exception.ExerciseNotFoundException;
import me.maxhub.hercules.mapper.ExerciseMapper;
import me.maxhub.hercules.repo.exercise.DifficultyRepository;
import me.maxhub.hercules.repo.exercise.ExerciseRepository;
import me.maxhub.hercules.repo.exercise.ExerciseTypeRepository;
import me.maxhub.hercules.repo.exercise.MuscleGroupRepository;
import me.maxhub.hercules.service.exercise.ExerciseFacade;
import me.maxhub.hercules.utils.Constants;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    public void createExercise(String subject, ExerciseRequestDto exerciseRequestDto) {
        exerciseRepository.save(getExerciseEntity(subject, exerciseRequestDto));
    }

    @Override
    @Transactional
    public void updateExercise(Jwt jwt, String id, ExerciseRequestDto exerciseRequestDto) {
        var userIds = getUserIds(jwt);
        var exerciseEntity = exerciseRepository.findByIdAndUserIdIn(id, userIds);
        if (exerciseEntity.isEmpty()) {
            throw new ExerciseNotFoundException(id);
        }

        var subject = jwt.getSubject();
        if (userIds.contains(Constants.SYSTEM_USER_ID)) {
            subject = Constants.SYSTEM_USER_ID;
        }
        var entityToSave = getExerciseEntity(subject, exerciseRequestDto);
        entityToSave.setId(id);
        exerciseRepository.save(entityToSave);
    }

    @Override
    public void deleteExercise(Jwt jwt, String id) {
        var userIds = getUserIds(jwt);
        if (!exerciseRepository.existsByIdAndUserIdIn(id, userIds)) {
            throw new ExerciseNotFoundException(id);
        }
        exerciseRepository.deleteById(id);
    }

    @Override
    public ExerciseResponseDto getExercise(String subject, String id) {
        return exerciseRepository.findByIdAndUserIdIn(id, List.of(subject, Constants.SYSTEM_USER_ID))
                .map(mapper::toDto)
                .orElseThrow(() -> new ExerciseNotFoundException(id));
    }

    @Override
    public Collection<ExerciseResponseDto> getExercises(String subject) {
        var exercises = exerciseRepository.findAllByUserIdIn(List.of(subject, Constants.SYSTEM_USER_ID));
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

    private ExerciseEntity getExerciseEntity(String subject, ExerciseRequestDto exerciseRequestDto) {
        var difficultyName = exerciseRequestDto.getDifficulty();
        var difficultyEntity = getDifficultyEntity(difficultyName);
        var exerciseTypeEntity = getExerciseTypeEntity(exerciseRequestDto);
        var entity = mapper.toEntity(exerciseRequestDto, difficultyEntity, exerciseTypeEntity, subject);
        entity.setMuscleGroups(createMuscleGroups(exerciseRequestDto.getMuscleGroups(), entity));
        return entity;
    }

    private DifficultyEntity getDifficultyEntity(String difficultyName) {
        return difficultyRepository.findByDifficultyName(difficultyName)
                .orElseGet(() -> difficultyRepository.save(new DifficultyEntity(difficultyName)));
    }

    private ExerciseTypeEntity getExerciseTypeEntity(ExerciseRequestDto exerciseRequestDto) {
        return exerciseTypeRepository.findByExerciseTypeName(exerciseRequestDto.getExerciseType())
                .orElseGet(() -> exerciseTypeRepository
                        .save(new ExerciseTypeEntity(exerciseRequestDto.getExerciseType())));
    }

    private List<String> getUserIds(Jwt jwt) {
        boolean isAdmin = false;
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess.containsKey("roles")) {
            List<String> roles = (List<String>) realmAccess.get("roles");
            if (roles.contains("admin")) {
                isAdmin = true;
            }
        }
        var userIds = new ArrayList<>(List.of(jwt.getSubject()));
        if (isAdmin) {
            userIds.add(Constants.SYSTEM_USER_ID);
        }
        return userIds;
    }
}
