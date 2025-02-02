package me.maxhub.hercules.entity.exercise;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "exercises")
public class ExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String imageId;
    private String gifId;
    private String videoId;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    private Integer setsCount;
    private Integer repsCount;
    private Long duration;
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<ExerciseMuscleGroupEntity> muscleGroups;
    @OneToOne
    @JoinColumn(nullable = false)
    private DifficultyEntity difficulty;
    @OneToOne
    @JoinColumn(nullable = false)
    private ExerciseTypeEntity exerciseType;
}
