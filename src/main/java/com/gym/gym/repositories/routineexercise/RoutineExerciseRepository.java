package com.gym.gym.repositories.routineexercise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineExerciseRepository extends JpaRepository<RoutineExerciseEntity, Long> {

	@Modifying
    @Query(value = "DELETE FROM RoutineExerciseEntity re where re.id IN(?1)")
    void deleteRoutineExercises(List<Long> ids);

	@Query(value = "SELECT re FROM RoutineExerciseEntity re where re.exercise.id = ?1")
	List<RoutineExerciseEntity> findbyExerciseId(Long exerciseId);
}
