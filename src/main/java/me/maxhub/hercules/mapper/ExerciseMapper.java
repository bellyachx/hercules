package me.maxhub.hercules.mapper;

import me.maxhub.hercules.dto.exercise.ExerciseRequestDto;
import me.maxhub.hercules.dto.exercise.ExerciseResponseDto;
import me.maxhub.hercules.entity.exercise.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ExerciseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "muscleGroups", ignore = true)
    @Mapping(target = "difficulty", source = "difficultyEntity")
    @Mapping(target = "exerciseType", source = "exerciseTypeEntity")
    @Mapping(target = "userId", source = "subject")
    public abstract ExerciseEntity toEntity(ExerciseRequestDto exerciseRequestDto,
                                            DifficultyEntity difficultyEntity,
                                            ExerciseTypeEntity exerciseTypeEntity,
                                            String subject);

    public abstract ExerciseResponseDto toDto(ExerciseEntity exerciseEntity);

    protected Long map(Duration duration) {
        return !Objects.isNull(duration) ? duration.getSeconds() : null;
    }

    protected Duration map(Long duration) {
        return !Objects.isNull(duration) ? Duration.ofSeconds(duration) : null;
    }

    protected String map(DifficultyEntity difficultyEntity) {
        return !Objects.isNull(difficultyEntity) ? difficultyEntity.getDifficultyName() : null;
    }

    protected String map(ExerciseTypeEntity exerciseType) {
        return !Objects.isNull(exerciseType) ? exerciseType.getExerciseTypeName() : null;
    }

    protected Collection<String> map(Collection<ExerciseMuscleGroupEntity> exerciseMuscleGroupEntities) {
        if (Objects.isNull(exerciseMuscleGroupEntities) || exerciseMuscleGroupEntities.isEmpty()) {
            return Collections.emptyList();
        }

        return exerciseMuscleGroupEntities.stream()
            .map(ExerciseMuscleGroupEntity::getMuscleGroup)
            .map(MuscleGroupEntity::getGroupName)
            .toList();
    }
}
