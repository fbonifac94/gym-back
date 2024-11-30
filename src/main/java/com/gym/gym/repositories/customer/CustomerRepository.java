package com.gym.gym.repositories.customer;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gym.gym.domain.enums.UserStatus;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

	@Query("SELECT c FROM CustomerEntity c WHERE c.user.id = :userId")
	Optional<CustomerEntity> findByUserId(@Param("userId") Long userId);

	@Query("SELECT c FROM CustomerEntity c WHERE (:status IS NULL OR c.user.status = :status) AND (:documentFilter IS NULL OR c.user.person.documetNumber LIKE %:documentFilter%)")
	Page<CustomerEntity> findAllPaginatedAndFiltered(@Param("documentFilter") String documentFilter, @Param("status") UserStatus status,
			PageRequest pageRequest);

}
