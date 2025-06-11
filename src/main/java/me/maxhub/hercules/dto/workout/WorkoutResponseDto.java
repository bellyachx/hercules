package me.maxhub.hercules.dto.workout;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.maxhub.hercules.dto.exercise.ExerciseResponseDto;
import me.maxhub.hercules.dto.workout.logs.WorkoutLogResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkoutResponseDto {
    private String id;
    private String name;
    private LocalDateTime date;
    private String notes;
    private List<ExerciseResponseDto> exercises;
    private List<WorkoutLogResponseDto> workoutLogs;
}
