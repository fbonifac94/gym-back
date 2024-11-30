package com.gym.gym.repositories.suscriptionpayment;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SuscriptionPaymentRepository extends JpaRepository<SuscriptionPaymentEntity, Long> {

	@Query("SELECT s FROM SuscriptionPaymentEntity s WHERE s.user.id = :userId")
	Page<SuscriptionPaymentEntity> findPaymentsByUserId(@Param("userId") Long userId, Pageable pageable);

	@Modifying
	@Query(value = "DELETE FROM SuscriptionPaymentEntity s where s.id = :suscriptionPaymentId")
	void deleteSuscriptionPaymentById(@Param("suscriptionPaymentId") Long suscriptionPaymentId);

	@Query(value = "SELECT s FROM SuscriptionPaymentEntity s "
			+ "WHERE (:documentFilter IS NULL OR s.user.person.documetNumber LIKE %:documentFilter%) "
			+ "AND(:startDate IS NULL OR s.date >= :startDate) "
			+ "AND(:endDate IS NULL OR s.date <= :endDate)")
	Page<SuscriptionPaymentEntity> findAllSuscriptionPayments(@Param("documentFilter") String documentFilter,
			@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageRequest);
}
