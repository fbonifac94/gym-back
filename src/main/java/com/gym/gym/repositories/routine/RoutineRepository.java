package com.gym.gym.repositories.routine;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineRepository extends JpaRepository<RoutineEntity, Long> {

	@Query("SELECT r FROM RoutineEntity r WHERE r.customer.user.id = :userId")
	Page<RoutineEntity> findRoutinesByUserId(@Param("userId") Long userId, Pageable pageable);
	
	@Modifying
	@Query("DELETE FROM RoutineEntity r where r.id IN(?1)")
	void deleteAllRoutinesById(List<Long> routinesId);

	@Query("SELECT r FROM RoutineEntity r JOIN r.exercises e WHERE e.exercise.id = ?1")
	List<RoutineEntity> findAllRoutinesByExerciseId(Long exerciseId);
}
