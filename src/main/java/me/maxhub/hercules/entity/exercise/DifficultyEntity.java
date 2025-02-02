package me.maxhub.hercules.entity.exercise;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "exercises_difficulty")
@NoArgsConstructor
public class DifficultyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, nullable = false)
    private String difficultyName;

    public DifficultyEntity(String difficultyName) {
        this.difficultyName = difficultyName;
    }
}
