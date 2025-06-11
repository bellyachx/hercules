package me.maxhub.hercules.dto.exercise;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExerciseResponseDto {
    private String id;
    private String name;
    private String description;
    private Integer setsCount;
    private Integer repsCount;
    private Duration duration;
    private Collection<String> muscleGroups;
    private String difficulty;
    private String exerciseType;
}
