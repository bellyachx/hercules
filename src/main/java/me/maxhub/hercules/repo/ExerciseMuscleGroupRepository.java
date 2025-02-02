package me.maxhub.hercules.repo;

import me.maxhub.hercules.entity.exercise.ExerciseMuscleGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseMuscleGroupRepository extends JpaRepository<ExerciseMuscleGroupEntity, String> {
}
