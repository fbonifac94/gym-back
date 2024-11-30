package com.gym.gym.repositories.exercise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {

	@Query("SELECT e FROM ExerciseEntity e WHERE e.exerciseType.id = :exerciseTypeId")
	List<ExerciseEntity> findByExerciseTypeId(@Param("exerciseTypeId") Long exerciseTypeId);
}
