package me.maxhub.hercules.controller.workout;

import lombok.RequiredArgsConstructor;
import me.maxhub.hercules.dto.workout.WorkoutRequestDto;
import me.maxhub.hercules.dto.workout.WorkoutResponseDto;
import me.maxhub.hercules.dto.workout.logs.ExerciseLogRequestDto;
import me.maxhub.hercules.dto.workout.logs.ExerciseLogResponseDto;
import me.maxhub.hercules.dto.workout.logs.WorkoutLogResponseDto;
import me.maxhub.hercules.dto.workout.logs.WorkoutLogStatus;
import me.maxhub.hercules.service.workout.WorkoutFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutFacade workoutFacade;

    @PostMapping
    public void createWorkout(@AuthenticationPrincipal Jwt jwt,
                              @RequestBody WorkoutRequestDto workoutRequestDto) {
        workoutFacade.createWorkout(jwt.getSubject(), workoutRequestDto);
    }

    @GetMapping
    public ResponseEntity<List<WorkoutResponseDto>> getWorkouts(@AuthenticationPrincipal Jwt jwt) {
        var workouts = workoutFacade.getWorkouts(jwt.getSubject());
        return ResponseEntity.ok(workouts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutResponseDto> getWorkout(@AuthenticationPrincipal Jwt jwt,
                                                         @PathVariable String id) {
        var workout = workoutFacade.getWorkout(jwt.getSubject(), id);
        return ResponseEntity.ok(workout);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WorkoutResponseDto> deleteWorkout(@AuthenticationPrincipal Jwt jwt,
                                                            @PathVariable String id) {
        var workout = workoutFacade.deleteWorkout(jwt.getSubject(), id);
        var status = workout == null ? HttpStatus.NOT_FOUND : HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status).body(workout);
    }

    @GetMapping("/{id}/logs")
    public ResponseEntity<List<WorkoutLogResponseDto>> getWorkoutLogs(@AuthenticationPrincipal Jwt jwt,
                                                                      @PathVariable("id") String id) {
        var workoutLogs = workoutFacade.getWorkoutLogs(jwt.getSubject(), id);
        return ResponseEntity.ok(workoutLogs);
    }

    @GetMapping("/logs/{id}")
    public ResponseEntity<WorkoutLogResponseDto> getWorkoutLog(@AuthenticationPrincipal Jwt jwt,
                                                               @PathVariable String id) {
        var workouts = workoutFacade.getWorkoutLog(jwt.getSubject(), id);
        return ResponseEntity.ok(workouts);
    }

    @GetMapping("/logs/{id}/exercises/logs")
    public ResponseEntity<List<ExerciseLogResponseDto>> getWorkoutExerciseLogs(@AuthenticationPrincipal Jwt jwt,
                                                                               @PathVariable("id") String id) {
        var exerciseLogs = workoutFacade.getExerciseLogs(jwt.getSubject(), id);
        return ResponseEntity.ok(exerciseLogs);
    }

    @GetMapping("/logs/exercises/logs/{id}")
    public ResponseEntity<ExerciseLogResponseDto> getExerciseLog(@AuthenticationPrincipal Jwt jwt,
                                                                 @PathVariable String id) {
        var exerciseLog = workoutFacade.getExerciseLog(jwt.getSubject(), id);
        return ResponseEntity.ok(exerciseLog);
    }

    @PostMapping("{id}/logs")
    public ResponseEntity<String> initWorkoutLog(@AuthenticationPrincipal Jwt jwt,
                                                 @PathVariable("id") String workoutId) {
        var workoutLogId = workoutFacade.initWorkoutLog(jwt.getSubject(), workoutId);
        return ResponseEntity.ok(workoutLogId);
    }

    @PutMapping("/logs/{id}")
    public void updateWorkoutLog(@AuthenticationPrincipal Jwt jwt,
                                 @PathVariable("id") String workoutLogId,
                                 @RequestParam("status") WorkoutLogStatus status) {
        workoutFacade.updateWorkoutLogStatus(jwt.getSubject(), workoutLogId, status);
    }

    @PutMapping("/logs/{id}/exercises/logs")
    public void submitExerciseLog(@AuthenticationPrincipal Jwt jwt,
                                  @PathVariable("id") String workoutLogId,
                                  @RequestBody ExerciseLogRequestDto exerciseLogRequestDto) {
        workoutFacade.submitExerciseLog(jwt.getSubject(), workoutLogId, exerciseLogRequestDto);
    }
}
