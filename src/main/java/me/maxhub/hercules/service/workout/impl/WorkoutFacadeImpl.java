package me.maxhub.hercules.service.workout.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.maxhub.hercules.dto.workout.WorkoutRequestDto;
import me.maxhub.hercules.dto.workout.WorkoutResponseDto;
import me.maxhub.hercules.dto.workout.logs.ExerciseLogRequestDto;
import me.maxhub.hercules.dto.workout.logs.ExerciseLogResponseDto;
import me.maxhub.hercules.dto.workout.logs.WorkoutLogResponseDto;
import me.maxhub.hercules.dto.workout.logs.WorkoutLogStatus;
import me.maxhub.hercules.entity.workout.WorkoutEntity;
import me.maxhub.hercules.entity.workout.WorkoutLogEntity;
import me.maxhub.hercules.mapper.ExerciseMapper;
import me.maxhub.hercules.mapper.WorkoutMapper;
import me.maxhub.hercules.repo.exercise.ExerciseRepository;
import me.maxhub.hercules.repo.workout.*;
import me.maxhub.hercules.service.workout.WorkoutFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutFacadeImpl implements WorkoutFacade {

    private final WorkoutMapper workoutMapper;
    private final ExerciseMapper exerciseMapper;
    private final WorkoutsRepository workoutsRepository;
    private final WorkoutLogsRepository workoutLogsRepository;
    private final ExerciseLogsRepository exerciseLogsRepository;
    private final WorkoutExercisesRepository workoutExercisesRepository;
    private final ExerciseWorkoutLogsRepository exerciseWorkoutLogsRepository;
    private final ExerciseRepository exerciseRepository;

    @Override
    @Transactional
    public void createWorkout(String subject, WorkoutRequestDto workoutRequestDto) {
        var workoutEntity = workoutMapper.toWorkoutEntity(workoutRequestDto, subject);
        var saved = workoutsRepository.save(workoutEntity);
        workoutRequestDto.getExercises().forEach(exerciseId -> {
            workoutExercisesRepository.save(exerciseId, saved.getId());
        });
    }

    @Override
    public List<WorkoutResponseDto> getWorkouts(String subject) {
        return workoutsRepository.findAllShort().stream()
            .map(workoutMapper::toWorkoutDto)
            .collect(Collectors.toList());
    }

    @Override
    public WorkoutResponseDto getWorkout(String subject, String id) {
        var workoutEntity = workoutsRepository.findById(id);
        var workoutResponseDtoOptional = workoutEntity.map(workoutMapper::toWorkoutDto);
        if (workoutResponseDtoOptional.isPresent()) {
            var workoutResponseDto = workoutResponseDtoOptional.get();
            var workoutLogs = workoutLogsRepository.findAllByWorkout(workoutEntity.get()).stream()
                .map(workoutMapper::toWorkoutLogDto)
                .collect(Collectors.toList());
            workoutResponseDto.setWorkoutLogs(workoutLogs);
            var exercises = exerciseRepository.findAllByWorkoutId(id).stream()
                .map(exerciseMapper::toDto)
                .collect(Collectors.toList());
            workoutResponseDto.setExercises(exercises);
            return workoutResponseDto;
        }
        return null;
    }

    @Override
    @Transactional
    public WorkoutResponseDto deleteWorkout(String subject, String id) {
        var workoutEntity = workoutsRepository.findById(id);
        workoutEntity.ifPresent(entity -> {
            var workoutLog = workoutLogsRepository.findByWorkoutId(id);
            workoutLog.forEach(workoutLogEntity -> {
                exerciseWorkoutLogsRepository.deleteByWorkoutLogId(workoutLogEntity.getId());
                workoutLogsRepository.delete(workoutLogEntity);
            });
            workoutExercisesRepository.deleteByWorkoutId(id);
            workoutsRepository.delete(entity);
        });
        return workoutEntity.map(workoutMapper::toWorkoutDto).orElse(null);
    }

    @Override
    public String initWorkoutLog(String subject, String workoutId) {
        var workoutLogEntity = new WorkoutLogEntity();
        workoutLogEntity.setStatus(WorkoutLogStatus.CREATED);
        workoutLogEntity.setUserId(subject);
        var workoutEntity = new WorkoutEntity();
        workoutEntity.setId(workoutId);
        workoutLogEntity.setWorkout(workoutEntity);
        workoutLogEntity.setCreatedAt(LocalDateTime.now());
        workoutLogEntity.setUpdatedAt(LocalDateTime.now());
        return workoutLogsRepository.save(workoutLogEntity).getId();
    }

    @Override
    public void updateWorkoutLogStatus(String subject, String workoutLogId, WorkoutLogStatus workoutLogStatus) {
        workoutLogsRepository.updateStatus(subject, workoutLogId, workoutLogStatus);
    }

    @Override
    public WorkoutLogResponseDto getWorkoutLog(String subject, String workoutLogId) {
        return workoutLogsRepository.findByIdAndUserId(workoutLogId, subject)
            .map(workoutMapper::toWorkoutLogDto)
            .orElse(null);
    }

    @Override
    public List<WorkoutLogResponseDto> getWorkoutLogs(String subject, String workoutId) {
        return workoutLogsRepository.findAllByUserIdAndWorkoutId(subject, workoutId).stream()
            .map(workoutMapper::toWorkoutLogDto)
            .collect(Collectors.toList());
    }

    @Override
    public ExerciseLogResponseDto getExerciseLog(String subject, String exerciseLogId) {
        return exerciseLogsRepository.findByIdAndUserId(exerciseLogId, subject).map(workoutMapper::toExerciseLogDto).orElse(null);
    }

    @Override
    public List<ExerciseLogResponseDto> getExerciseLogs(String subject, String workoutLogId) {
        return exerciseLogsRepository.findAllByUserIdAndWorkoutLogId(subject, workoutLogId).stream()
            .map(workoutMapper::toExerciseLogDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void submitExerciseLog(String subject, String workoutLogId, ExerciseLogRequestDto exerciseLogRequestDto) {
        var exerciseLogEntity = workoutMapper.toExerciseLogEntity(exerciseLogRequestDto, subject);
        var exerciseId = exerciseLogRequestDto.getExerciseId();
        var exerciseEntity = exerciseRepository.findById(exerciseId)
            .orElseThrow(() -> new IllegalArgumentException("Exercise not found with ID: " + exerciseId));
        exerciseLogEntity.setExercise(exerciseEntity);
        var saved = exerciseLogsRepository.save(exerciseLogEntity);
        exerciseWorkoutLogsRepository.save(workoutLogId, saved.getId());
        workoutLogsRepository.updateStatus(subject, workoutLogId, WorkoutLogStatus.IN_PROGRESS);
    }
}
