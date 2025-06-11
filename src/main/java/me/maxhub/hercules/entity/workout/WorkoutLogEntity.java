package me.maxhub.hercules.entity.workout;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import me.maxhub.hercules.dto.workout.logs.WorkoutLogStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "workout_logs")
public class WorkoutLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    @JoinColumn
    @ManyToOne
    private WorkoutEntity workout;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    private WorkoutLogStatus status;
    private String notes;
}
