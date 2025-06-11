package me.maxhub.hercules.dto.workout.logs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseLogResponseDto {
    private String id;
    private String exerciseId;
    private LocalDateTime date;
    private Integer sets;
    private Integer reps;
    private Long duration;
    private String notes;
}
