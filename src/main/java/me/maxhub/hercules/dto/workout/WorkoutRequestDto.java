package me.maxhub.hercules.dto.workout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRequestDto {
    private String name;
    private String notes;
    private List<String> exercises;
}
