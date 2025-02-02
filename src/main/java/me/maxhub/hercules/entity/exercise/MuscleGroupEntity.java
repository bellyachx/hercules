package me.maxhub.hercules.entity.exercise;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "muscle_groups")
@NoArgsConstructor
public class MuscleGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, nullable = false)
    private String groupName;

    public MuscleGroupEntity(String groupName) {
        this.groupName = groupName;
    }
}
