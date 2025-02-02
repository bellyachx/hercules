package me.maxhub.hercules.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseRequestDto {
    @NotNull
    private String name;
    private String description;
    private Integer setsCount;
    private Integer repsCount;
    private Duration duration;
    private Set<String> muscleGroups;
    private String difficulty;
    private String exerciseType;
}
