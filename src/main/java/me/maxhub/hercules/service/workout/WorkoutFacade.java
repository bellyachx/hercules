package me.maxhub.hercules.service.workout;

import me.maxhub.hercules.dto.workout.WorkoutRequestDto;
import me.maxhub.hercules.dto.workout.WorkoutResponseDto;
import me.maxhub.hercules.dto.workout.logs.ExerciseLogRequestDto;
import me.maxhub.hercules.dto.workout.logs.ExerciseLogResponseDto;
import me.maxhub.hercules.dto.workout.logs.WorkoutLogResponseDto;
import me.maxhub.hercules.dto.workout.logs.WorkoutLogStatus;

import java.util.List;

public interface WorkoutFacade {

    void createWorkout(String subject, WorkoutRequestDto workoutRequestDto);

    List<WorkoutResponseDto> getWorkouts(String subject);

    WorkoutResponseDto getWorkout(String subject, String id);

    WorkoutResponseDto deleteWorkout(String subject, String id);

    String initWorkoutLog(String subject, String workoutId);

    void updateWorkoutLogStatus(String subject, String workoutLogId, WorkoutLogStatus workoutLogStatus);

    WorkoutLogResponseDto getWorkoutLog(String subject, String workoutLogId);

    List<WorkoutLogResponseDto> getWorkoutLogs(String subject, String workoutId);

    ExerciseLogResponseDto getExerciseLog(String subject, String exerciseLogId);

    List<ExerciseLogResponseDto> getExerciseLogs(String subject, String workoutLogId);

    void submitExerciseLog(String subject, String workoutLogId, ExerciseLogRequestDto exerciseLogRequestDto);

}
