package me.maxhub.hercules.entity.exercise;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "exercise_muscle_groups")
public class ExerciseMuscleGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private ExerciseEntity exercise;
    @ManyToOne
    @JoinColumn(nullable = false)
    private MuscleGroupEntity muscleGroup;
}
