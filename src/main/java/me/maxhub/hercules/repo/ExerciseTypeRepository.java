package me.maxhub.hercules.repo;

import me.maxhub.hercules.entity.exercise.ExerciseTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseTypeRepository extends JpaRepository<ExerciseTypeEntity, String> {

    Optional<ExerciseTypeEntity> findByExerciseTypeName(String exerciseTypeName);
}
