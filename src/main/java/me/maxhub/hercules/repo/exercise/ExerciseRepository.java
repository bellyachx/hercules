package me.maxhub.hercules.repo.exercise;

import me.maxhub.hercules.entity.exercise.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, String> {

    Optional<ExerciseEntity> findByIdAndUserIdIn(String id, List<String> userIds);

    List<ExerciseEntity> findAllByUserIdIn(List<String> userIds);

    boolean existsByIdAndUserIdIn(String id, List<String> userIds);
}
