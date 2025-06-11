package me.maxhub.hercules.entity.workout;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import me.maxhub.hercules.entity.exercise.ExerciseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "exercise_logs")
public class ExerciseLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    @OneToOne
    @JoinColumn(nullable = false)
    private ExerciseEntity exercise;
    private LocalDateTime date;
    private Integer sets;
    private Integer reps;
    private Long duration;
    private String notes;
}
