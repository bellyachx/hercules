package me.maxhub.hercules.repo.workout;

import jakarta.transaction.Transactional;
import me.maxhub.hercules.dto.workout.logs.WorkoutShortDto;
import me.maxhub.hercules.entity.workout.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutsRepository extends JpaRepository<WorkoutEntity, String> {

    @Query(value = "select new me.maxhub.hercules.dto.workout.logs.WorkoutShortDto(w.id, w.name) from WorkoutEntity w")
    List<WorkoutShortDto> findAllShort();

    @Modifying
    @Transactional
    @Query(value = "delete from workouts where id = ?2 and user_id = ?1", nativeQuery = true)
    int deleteByIdWithReturn(String subject, String id);
}
