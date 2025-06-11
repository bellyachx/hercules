package me.maxhub.hercules.entity.workout;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "exercise_workout_logs")
public class ExerciseWorkoutLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private ExerciseLogEntity exerciseLog;
    @ManyToOne
    @JoinColumn(nullable = false)
    private WorkoutLogEntity workoutLog;
}
