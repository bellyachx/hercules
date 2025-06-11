package me.maxhub.hercules.entity.workout;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import me.maxhub.hercules.entity.exercise.ExerciseEntity;

@Setter
@Getter
@Entity
@Table(name = "workout_exercises")
public class WorkoutExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private ExerciseEntity exercise;
    @ManyToOne
    @JoinColumn(nullable = false)
    private WorkoutEntity workout;
}
