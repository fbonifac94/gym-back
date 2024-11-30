package com.gym.gym.service;

import static com.gym.gym.domain.enums.Status.BA;
import static com.gym.gym.domain.enums.Status.HA;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gym.gym.domain.ScheduledClaszInscription;
import com.gym.gym.repositories.clasz.ClaszEntity;
import com.gym.gym.repositories.clasz.ClaszRepository;
import com.gym.gym.repositories.customer.CustomerEntity;
import com.gym.gym.repositories.scheduledclasz.ScheduledClaszEntity;
import com.gym.gym.repositories.scheduledclaszinscription.ScheduledClaszInscriptionEntity;
import com.gym.gym.repositories.scheduledclaszinscription.ScheduledClaszInscriptionRepository;
import com.gym.gym.service.mapper.ScheduledClaszInscriptionMapper;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduledClaszInscriptionService {

	private final ScheduledClaszInscriptionRepository scheduledClaszInscriptionRepository;

	private final ScheduledClaszInscriptionMapper scheduledClaszInscriptionMapper;

	private final CustomerService customerService;

	private final ScheduledClaszService scheduledClaszService;

	private final ClaszRepository claszRepository;

	@Transactional
	public void enrollToClass(Long userId, Long scheduledClassId) {
		CustomerEntity customer = customerService.findByUserId(userId);

		ScheduledClaszEntity scheduledClasz = scheduledClaszService.findById(scheduledClassId);

		scheduledClaszInscriptionRepository.saveAndFlush(new ScheduledClaszInscriptionEntity(scheduledClasz, customer));

		scheduledClasz.setActualClassPersonsAmount(scheduledClasz.getActualClassPersonsAmount() + 1);

		scheduledClaszService.saveScheduledClasz(scheduledClasz);
	}

	@Transactional
	public void eraseClassInscription(Long userId, Long scheduledClassId) {
		CustomerEntity customer = customerService.findByUserId(userId);

		ScheduledClaszEntity scheduledClasz = scheduledClaszService.findById(scheduledClassId);

		ScheduledClaszInscriptionEntity scheduledClaszInscription = scheduledClaszInscriptionRepository
				.findByScheduledClaszAndCustomer(scheduledClasz, customer);

		scheduledClaszInscriptionRepository.delete(scheduledClaszInscription);
		scheduledClaszInscriptionRepository.flush();

		Integer newAmountOfPersons = scheduledClasz.getActualClassPersonsAmount() - 1;

		scheduledClasz.setActualClassPersonsAmount((newAmountOfPersons < 0) ? 0 : newAmountOfPersons);

		scheduledClaszService.saveScheduledClasz(scheduledClasz);
	}

	public List<ScheduledClaszInscription> getSheduledClaszesInscriptionByScheduledClassId(Long scheduledClassId) {
		ScheduledClaszEntity scheduledClasz = scheduledClaszService.findById(scheduledClassId);

		List<ScheduledClaszInscriptionEntity> scheduledClaszInscription = scheduledClaszInscriptionRepository
				.findByScheduledClasz(scheduledClasz);

		return scheduledClaszInscriptionMapper.entityListToDomainList(scheduledClaszInscription);
	}

	public List<ScheduledClaszInscription> getSheduledClaszesCustotmerInscriptionByUserId(Long userId) {
		CustomerEntity customer = customerService.findByUserId(userId);

		List<ScheduledClaszInscriptionEntity> scheduledClaszInscription = scheduledClaszInscriptionRepository
				.findByUserIdAndActive(customer.getUser().getId(), LocalDateTime.now());

		return scheduledClaszInscriptionMapper.entityListToDomainList(scheduledClaszInscription);
	}

	@Transactional
	public void cancelClassInscriptionsFromCustomerByUserId(Long userId) {
		CustomerEntity customer = customerService.findByUserId(userId);

		List<ScheduledClaszInscriptionEntity> scheduledClaszInscriptions = scheduledClaszInscriptionRepository
				.findByCustomerIdAndStatus(customer.getId(), HA);

		List<ScheduledClaszEntity> scheduledClaszes = scheduledClaszInscriptions.stream()
				.map(ScheduledClaszInscriptionEntity::getScheduledClasz).toList();

		scheduledClaszInscriptionRepository.deleteAll(scheduledClaszInscriptions);
		scheduledClaszInscriptionRepository.flush();

		scheduledClaszes = scheduledClaszes.stream().map(scheduledClasz -> {
			Integer newAmountOfPersons = scheduledClasz.getActualClassPersonsAmount() - 1;
			scheduledClasz.setActualClassPersonsAmount((newAmountOfPersons < 0) ? 0 : newAmountOfPersons);
			return scheduledClasz;
		}).toList();

		scheduledClaszService.saveScheduledClaszes(scheduledClaszes);
	}

	@Transactional
	public void cancelAllScheduledClaszesAndInscriptionsFromTeacherByUserId(Long userId) {
		List<ScheduledClaszEntity> scheduledClaszes = scheduledClaszService.findNonExpiredByTeacherUserId(userId);

		scheduledClaszes.forEach(scheduledClasz -> scheduledClaszService.cancelScheduledClasz(scheduledClasz.getId()));

		List<ClaszEntity> claszes = scheduledClaszes.stream().map(ScheduledClaszEntity::getClasz).toList();

		claszes.forEach(elem -> elem.setStatus(BA));

		claszRepository.saveAllAndFlush(claszes);
	}
}
