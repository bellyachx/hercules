package me.maxhub.hercules.service.exercise.impl;

import lombok.RequiredArgsConstructor;
import me.maxhub.hercules.entity.exercise.MuscleGroupEntity;
import me.maxhub.hercules.repo.exercise.MuscleGroupRepository;
import me.maxhub.hercules.service.exercise.MuscleGroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MuscleGroupServiceImpl implements MuscleGroupService {

    private final MuscleGroupRepository muscleGroupRepository;

    @Override
    public void createMuscleGroup(String muscleGroupName) {
        var muscleGroupEntity = new MuscleGroupEntity(muscleGroupName);
        muscleGroupRepository.save(muscleGroupEntity);
    }

    @Override
    public void deleteMuscleGroup(String id) {
        muscleGroupRepository.deleteById(id);
    }

    @Override
    public void deleteMuscleGroupByName(String muscleGroupName) {
        muscleGroupRepository.deleteByGroupName(muscleGroupName);
    }

    @Override
    public List<String> getMuscleGroups() {
        return muscleGroupRepository.findAll().stream()
            .map(MuscleGroupEntity::getGroupName)
            .collect(Collectors.toList());
    }
}
