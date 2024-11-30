package com.gym.gym.service;

import static com.gym.gym.domain.enums.SuscriptionTimeUnits.DAY;
import static com.gym.gym.domain.enums.SuscriptionTimeUnits.MONTH;
import static com.gym.gym.domain.enums.SuscriptionTimeUnits.YEAR;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gym.gym.controllers.suscriptionpayment.PostSuscriptionRequest;
import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.domain.SuscriptionPayment;
import com.gym.gym.domain.enums.SuscriptionTimeUnits;
import com.gym.gym.domain.enums.UserStatus;
import com.gym.gym.repositories.suscriptionpayment.SuscriptionPaymentEntity;
import com.gym.gym.repositories.suscriptionpayment.SuscriptionPaymentRepository;
import com.gym.gym.repositories.user.UserEntity;
import com.gym.gym.service.mapper.SuscriptionPaymentMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SuscriptionPaymentService {

	private final SuscriptionPaymentRepository suscriptionPaymentRepository;

	private final SuscriptionPaymentMapper suscriptionPaymentMapper;

	private final UserService userService;

	@Transactional
	public void postPayment(Long userId, PostSuscriptionRequest request) {
		UserEntity userEntity = userService.findById(userId);
		LocalDate newExpireDate = processNewExpireDate(userEntity.getExpireDate(), request.getSuscriptionQuantity(),
				request.getSusucriptionQuantityTimeUnits());
		SuscriptionPaymentEntity suscriptionPaymentEntity = new SuscriptionPaymentEntity(request.getAmount(),
				LocalDateTime.now(), request.getSuscriptionQuantity(), request.getSusucriptionQuantityTimeUnits(),
				userEntity.getExpireDate(), newExpireDate, userEntity);
		userEntity.setExpireDate(newExpireDate);
		userEntity.setStatus(UserStatus.HA);
		suscriptionPaymentRepository.save(suscriptionPaymentEntity);
		userService.saveUser(userEntity);
	}

	@Transactional
	public void deletePayment(Long suscriptionPaymentId) {
		SuscriptionPaymentEntity suscriptionPaymentEntity = suscriptionPaymentRepository.findById(suscriptionPaymentId)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("No se encontró el pago de suscripción con id %s", suscriptionPaymentId)));
		UserEntity userEntity = userService.findById(suscriptionPaymentEntity.getUser().getId());
		userEntity.setExpireDate(suscriptionPaymentEntity.getOldExpireDate());
		if (Objects.isNull(suscriptionPaymentEntity.getOldExpireDate())
				|| suscriptionPaymentEntity.getOldExpireDate().isBefore(LocalDate.now())) {
			userEntity.setStatus(UserStatus.BA);
		}
		suscriptionPaymentRepository.deleteSuscriptionPaymentById(suscriptionPaymentId);
		userService.saveUser(userEntity);
	}

	public PaginatedObject<SuscriptionPayment> getPaymentsFromUser(Integer pageNumber, Integer pageSize, Long userId) {
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.DESC, "date"));

		Page<SuscriptionPaymentEntity> paginatedSuscriptionPayments = suscriptionPaymentRepository
				.findPaymentsByUserId(userId, pageRequest);

		return new PaginatedObject<SuscriptionPayment>(
				suscriptionPaymentMapper.entityListToDomainList(paginatedSuscriptionPayments.getContent()),
				paginatedSuscriptionPayments.getPageable().getPageNumber(),
				paginatedSuscriptionPayments.getNumberOfElements(), paginatedSuscriptionPayments.getTotalPages(),
				paginatedSuscriptionPayments.getTotalElements());
	}

	public PaginatedObject<SuscriptionPayment> getSuscriptionPayments(String documentNumber, LocalDate startDate,
			LocalDate endDate, Integer pageNumber, Integer pageSize) {
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.DESC, "date"));

		Page<SuscriptionPaymentEntity> paginatedSuscriptionPayments = suscriptionPaymentRepository
				.findAllSuscriptionPayments(documentNumber, startDate, endDate, pageRequest);

		return new PaginatedObject<SuscriptionPayment>(
				suscriptionPaymentMapper.entityListToDomainList(paginatedSuscriptionPayments.getContent()),
				paginatedSuscriptionPayments.getPageable().getPageNumber(),
				paginatedSuscriptionPayments.getNumberOfElements(), paginatedSuscriptionPayments.getTotalPages(),
				paginatedSuscriptionPayments.getTotalElements());
	}

	private LocalDate processNewExpireDate(LocalDate userExpireDate, Long suscriptionQuantity,
			SuscriptionTimeUnits suscriptionQuantityUnits) {
		LocalDate expireDate = userExpireDate;
		LocalDate newExpireDate = null;
		if (Objects.isNull(expireDate) || LocalDate.now().isAfter(expireDate)) {
			LocalDate today = LocalDate.now();
			if (DAY.equals(suscriptionQuantityUnits)) {
				newExpireDate = today.minusDays(1).plusDays(suscriptionQuantity);
			}
			if (MONTH.equals(suscriptionQuantityUnits)) {
				newExpireDate = today.plusMonths(suscriptionQuantity);
			}
			if (YEAR.equals(suscriptionQuantityUnits)) {
				newExpireDate = today.plusYears(suscriptionQuantity);
			}
		} else {
			if (DAY.equals(suscriptionQuantityUnits)) {
				newExpireDate = expireDate.plusDays(suscriptionQuantity);
			}
			if (MONTH.equals(suscriptionQuantityUnits)) {
				newExpireDate = expireDate.plusMonths(suscriptionQuantity);
			}
			if (YEAR.equals(suscriptionQuantityUnits)) {
				newExpireDate = expireDate.plusYears(suscriptionQuantity);
			}
		}
		return newExpireDate;
	}
}
