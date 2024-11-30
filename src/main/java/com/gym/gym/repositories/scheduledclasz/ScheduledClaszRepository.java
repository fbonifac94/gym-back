package com.gym.gym.repositories.scheduledclasz;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gym.gym.repositories.clasz.ClaszEntity;
import com.gym.gym.repositories.teacher.TeacherEntity;

@Repository
public interface ScheduledClaszRepository extends JpaRepository<ScheduledClaszEntity, Long> {

	Optional<ScheduledClaszEntity> findByClaszAndInitDateTimeAndEndDateTimeAndTeacher(ClaszEntity clasz,
			LocalDateTime initDateTime, LocalDateTime endDateTime, TeacherEntity teacher);

	@Query("SELECT sc FROM ScheduledClaszEntity sc WHERE sc.teacher.user.id = :userId AND sc.initDateTime >= :now AND sc.status ='HA'")
	Page<ScheduledClaszEntity> findNonExpiredByTeacherUserId(@Param("userId") Long userId,
			@Param("now") LocalDateTime now, Pageable pageable);

	@Query("SELECT sc FROM ScheduledClaszEntity sc WHERE sc.teacher.user.id = :userId AND sc.initDateTime >= :now AND sc.status ='HA'")
	List<ScheduledClaszEntity> findNonExpiredByTeacherUserId(@Param("userId") Long userId,
			@Param("now") LocalDateTime now);

	@Query("SELECT sc FROM ScheduledClaszEntity sc WHERE sc.initDateTime >= :now AND sc.status ='HA'")
	Page<ScheduledClaszEntity> findNonExpired(@Param("now") LocalDateTime now, Pageable pageable);
	
	@Query("SELECT sc FROM ScheduledClaszEntity sc WHERE sc.initDateTime < :now AND sc.status ='HA'")
	List<ScheduledClaszEntity> findexpired(@Param("now") LocalDateTime now);
	
	
}
