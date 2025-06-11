package me.maxhub.hercules.repo.workout;

import jakarta.transaction.Transactional;
import me.maxhub.hercules.entity.workout.WorkoutExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutExercisesRepository extends JpaRepository<WorkoutExerciseEntity, String> {

    @Modifying
    @Transactional
    @Query(value = "insert into workout_exercises(exercise_id, workout_id) values (?1, ?2)", nativeQuery = true)
    void save(String exerciseId, String workoutId);

    @Modifying
    @Transactional
    void deleteByWorkoutId(String workoutId);
}
