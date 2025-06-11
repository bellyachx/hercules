package me.maxhub.hercules.repo.workout;

import jakarta.transaction.Transactional;
import me.maxhub.hercules.dto.workout.logs.WorkoutLogStatus;
import me.maxhub.hercules.entity.workout.WorkoutEntity;
import me.maxhub.hercules.entity.workout.WorkoutLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutLogsRepository extends JpaRepository<WorkoutLogEntity, String> {

    @Modifying
    @Transactional
    @Query(value = "update WorkoutLogEntity set status = ?3, updatedAt = current_timestamp where id = ?2 and userId = ?1")
    void updateStatus(String subject, String id, WorkoutLogStatus workoutLogStatus);

    Optional<WorkoutLogEntity> findByIdAndUserId(String id, String userId);

    @Query(value = "select * from workout_logs where user_id = ?1 and workout_id = ?2", nativeQuery = true)
    List<WorkoutLogEntity> findAllByUserIdAndWorkoutId(String userId, String workoutId);

    List<WorkoutLogEntity> findAllByWorkout(WorkoutEntity workout);

    List<WorkoutLogEntity> findByWorkoutId(String workoutId);
}
