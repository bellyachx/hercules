package me.maxhub.hercules.mapper;

import me.maxhub.hercules.dto.workout.WorkoutRequestDto;
import me.maxhub.hercules.dto.workout.WorkoutResponseDto;
import me.maxhub.hercules.dto.workout.logs.ExerciseLogRequestDto;
import me.maxhub.hercules.dto.workout.logs.ExerciseLogResponseDto;
import me.maxhub.hercules.dto.workout.logs.WorkoutLogResponseDto;
import me.maxhub.hercules.dto.workout.logs.WorkoutShortDto;
import me.maxhub.hercules.entity.workout.ExerciseLogEntity;
import me.maxhub.hercules.entity.workout.WorkoutEntity;
import me.maxhub.hercules.entity.workout.WorkoutLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkoutMapper {

    @Mapping(target = "date", expression = "java( java.time.LocalDateTime.now() )")
    @Mapping(target = "userId", source = "subject")
    WorkoutEntity toWorkoutEntity(WorkoutRequestDto workoutRequestDto, String subject);

    WorkoutResponseDto toWorkoutDto(WorkoutEntity workoutEntity);

    WorkoutResponseDto toWorkoutDto(WorkoutShortDto workoutShortDto);

    WorkoutLogResponseDto toWorkoutLogDto(WorkoutLogEntity workoutLogEntity);

    @Mapping(target = "date", expression = "java( java.time.LocalDateTime.now() )")
    @Mapping(target = "userId", source = "subject")
    ExerciseLogEntity toExerciseLogEntity(ExerciseLogRequestDto exerciseLogRequestDto, String subject);

    ExerciseLogResponseDto toExerciseLogDto(ExerciseLogEntity exerciseLogEntity);
}
