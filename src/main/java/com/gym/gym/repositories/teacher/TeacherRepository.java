package com.gym.gym.repositories.teacher;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gym.gym.domain.enums.UserStatus;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {

	@Query("SELECT t FROM TeacherEntity t WHERE t.user.id = :userId")
	Optional<TeacherEntity> findByUserId(@Param("userId") Long userId);

	@Query("SELECT t FROM TeacherEntity t WHERE (:documentFilter IS NULL OR t.user.person.documetNumber LIKE %:documentFilter%)")
	Page<TeacherEntity> findAllPaginatedAndFiltered(@Param("documentFilter") String documentFilter,
			PageRequest pageRequest);

	@Query("SELECT t FROM TeacherEntity t WHERE t.user.status = :status ")
	List<TeacherEntity> findAllByStatus(@Param("status") UserStatus status);

}
