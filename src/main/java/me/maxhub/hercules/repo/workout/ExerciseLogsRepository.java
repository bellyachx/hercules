package me.maxhub.hercules.repo.workout;

import me.maxhub.hercules.entity.workout.ExerciseLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseLogsRepository extends JpaRepository<ExerciseLogEntity, String> {

    Optional<ExerciseLogEntity> findByIdAndUserId(String id, String userId);

    @Query(value = "select el.* from exercise_logs el left join exercise_workout_logs ewl on el.id = ewl.exercise_log_id " +
        "where el.user_id = ?1 and ewl.workout_log_id = ?2", nativeQuery = true)
    List<ExerciseLogEntity> findAllByUserIdAndWorkoutLogId(String userId, String workoutId);

}
