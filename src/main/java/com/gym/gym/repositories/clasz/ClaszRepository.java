package com.gym.gym.repositories.clasz;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gym.gym.domain.enums.Status;

@Repository
public interface ClaszRepository extends JpaRepository<ClaszEntity, Long> {

	@Query("SELECT c FROM ClaszEntity c WHERE c.teacher.id = :teacherId AND c.day.id = :dayId AND " +
		       "(:initSchedule < c.endSchedule.time AND :endSchedule > c.initSchedule.time)")
	List<ClaszEntity> findConflictingClasses(@Param("teacherId") Long teacherId, @Param("dayId") Long dayId,
			@Param("initSchedule") LocalTime initSchedule, @Param("endSchedule") LocalTime endSchedule);

	List<ClaszEntity> findAllByStatus(Status status);

	@Query("SELECT c FROM ClaszEntity c WHERE c.status = 'HA' AND c.teacher.user.status = 'HA'")
	List<ClaszEntity> findAllAvailableClaszes();

}
