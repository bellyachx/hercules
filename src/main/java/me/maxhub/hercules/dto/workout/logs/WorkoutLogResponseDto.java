package me.maxhub.hercules.dto.workout.logs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkoutLogResponseDto {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String notes;
    private WorkoutLogStatus status;
}
