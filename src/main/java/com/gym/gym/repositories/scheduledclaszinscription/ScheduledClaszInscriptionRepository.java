package com.gym.gym.repositories.scheduledclaszinscription;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gym.gym.domain.enums.Status;
import com.gym.gym.repositories.customer.CustomerEntity;
import com.gym.gym.repositories.scheduledclasz.ScheduledClaszEntity;

@Repository
public interface ScheduledClaszInscriptionRepository extends JpaRepository<ScheduledClaszInscriptionEntity, Long> {

	ScheduledClaszInscriptionEntity findByScheduledClaszAndCustomer(ScheduledClaszEntity scheduledClasz,
			CustomerEntity customer);

	@Query("SELECT sci FROM ScheduledClaszInscriptionEntity sci WHERE sci.customer.id = :customerId AND sci.scheduledClasz.status = :status")
	List<ScheduledClaszInscriptionEntity> findByCustomerIdAndStatus(@Param("customerId") Long customerId,
			@Param("status") Status status);

	List<ScheduledClaszInscriptionEntity> findByScheduledClasz(ScheduledClaszEntity scheduledClasz);

	@Query("SELECT sci FROM ScheduledClaszInscriptionEntity sci WHERE sci.customer.user.id = :userId "
			+ "AND sci.scheduledClasz.status = 'HA' AND sci.scheduledClasz.initDateTime >= :now")
	List<ScheduledClaszInscriptionEntity> findByUserIdAndActive(@Param("userId") Long userId,
			@Param("now") LocalDateTime now);

}
