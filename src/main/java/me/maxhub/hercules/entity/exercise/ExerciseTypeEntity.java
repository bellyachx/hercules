package me.maxhub.hercules.entity.exercise;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "exercise_types")
@NoArgsConstructor
public class ExerciseTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, nullable = false)
    private String exerciseTypeName;

    public ExerciseTypeEntity(String exerciseTypeName) {
        this.exerciseTypeName = exerciseTypeName;
    }
}
