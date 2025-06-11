package me.maxhub.hercules.repo.workout;

import jakarta.transaction.Transactional;
import me.maxhub.hercules.entity.workout.ExerciseWorkoutLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseWorkoutLogsRepository extends JpaRepository<ExerciseWorkoutLogEntity, String> {

    @Modifying
    @Transactional
    @Query(value = "insert into exercise_workout_logs(workout_log_id, exercise_log_id) " +
        "values(?1, ?2)", nativeQuery = true)
    void save(String workoutLogId, String exerciseLogId);

    void deleteByWorkoutLogId(String workoutLogId);
}
