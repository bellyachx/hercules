package me.maxhub.hercules.dto.workout.logs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseLogRequestDto {
    private String exerciseId;
    private Integer sets;
    private Integer reps;
    private Long duration;
    private String notes;
}
