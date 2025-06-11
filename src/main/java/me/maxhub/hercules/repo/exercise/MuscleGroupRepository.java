package me.maxhub.hercules.repo.exercise;

import jakarta.transaction.Transactional;
import me.maxhub.hercules.entity.exercise.MuscleGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroupEntity, String> {

    Collection<MuscleGroupEntity> findAllByGroupNameIn(Collection<String> groupNames);

    @Transactional
    void deleteByGroupName(String groupName);
}
