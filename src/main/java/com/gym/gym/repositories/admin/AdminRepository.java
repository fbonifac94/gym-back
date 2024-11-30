package com.gym.gym.repositories.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

	@Query("SELECT a FROM AdminEntity a WHERE (:documentFilter IS NULL OR a.user.person.documetNumber LIKE %:documentFilter%)")
	Page<AdminEntity> findAllPaginatedAndFiltered(@Param("documentFilter") String documentfilter,
			PageRequest pageRequest);

}
