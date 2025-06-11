package me.maxhub.hercules.repo.exercise;

import jakarta.transaction.Transactional;
import me.maxhub.hercules.entity.exercise.DifficultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DifficultyRepository extends JpaRepository<DifficultyEntity, String> {

    Optional<DifficultyEntity> findByDifficultyName(String difficultyName);

    @Transactional
    void deleteByDifficultyName(String difficultyName);
}
