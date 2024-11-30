package com.gym.gym.service;

import static com.gym.gym.domain.enums.Role.CUSTOMER;
import static com.gym.gym.domain.enums.Role.TEACHER;
import static com.gym.gym.domain.enums.UserStatus.BA;
import static com.gym.gym.domain.enums.UserStatus.HA;
import static com.gym.gym.errors.Errors.USER_NOTFOUND_EMAIL_MSG_ERROR;
import static com.gym.gym.errors.Errors.USER_NOTFOUND_MSG_ERROR;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gym.gym.controllers.user.UpdatePersonRequest;
import com.gym.gym.domain.User;
import com.gym.gym.domain.enums.UserStatus;
import com.gym.gym.repositories.user.UserEntity;
import com.gym.gym.repositories.user.UserRepository;
import com.gym.gym.service.mapper.UserMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	private final ScheduledClaszInscriptionService scheduledClaszInscriptionService;

	@Transactional
	public void unsuscribeUser(Long userId) {
		findAndUpdateUserStatus(userId, BA);
	}

	@Transactional
	public void suscribeUser(Long userId) {
		findAndUpdateUserStatus(userId, HA);
	}

	@Transactional
	private void findAndUpdateUserStatus(Long userId, UserStatus userStatus) {
		UserEntity userEntity = findById(userId);
		userEntity.setStatus(userStatus);
		userRepository.saveAndFlush(userEntity);

		if (userEntity.getRole().getName().equals(TEACHER) && userEntity.getStatus().equals(BA)) {
			scheduledClaszInscriptionService.cancelAllScheduledClaszesAndInscriptionsFromTeacherByUserId(userId);
		}

		if (userEntity.getRole().getName().equals(CUSTOMER) && userEntity.getStatus().equals(BA)) {
			scheduledClaszInscriptionService.cancelClassInscriptionsFromCustomerByUserId(userId);
		}
	}

	public User findByUserId(Long userId) {
		UserEntity userEntity = findById(userId);
		return this.userMapper.entityToDomain(userEntity);
	}

	public UserEntity findById(Long id) {
		return this.userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(USER_NOTFOUND_MSG_ERROR.withParams(id)));
	}

	public UserEntity findByEmail(String email) {
		return this.userRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException(USER_NOTFOUND_EMAIL_MSG_ERROR.withParams(email)));
	}

	@Transactional
	public void updateUserByUserId(Long id, @Valid UpdatePersonRequest request) {
		UserEntity userEntity = findById(id);
		userEntity.getPerson().setFirstName(request.getFirstName());
		userEntity.getPerson().setLastName(request.getLastName());
		userEntity.getPerson().setPhone(request.getPhone());
		userEntity.getPerson().setBornDate(request.getBornDate());
		userEntity.getPerson().setCountry(request.getCountry());
		userEntity.getPerson().setCity(request.getCity());
		userEntity.getPerson().setDistrict(request.getDistrict());
		userEntity.getPerson().setAddress(request.getAddress());
		userEntity.getPerson().setDocumentType(request.getDocumentType());
		userEntity.getPerson().setDocumetNumber(request.getDocumetNumber());
		saveUser(userEntity);
	}

	@Transactional
	public void saveUser(UserEntity user) {
		this.userRepository.saveAndFlush(user);

	}

	@Transactional
	@Scheduled(cron = "0 0 0 * * *")
	public void updateUserStatus() {
		List<UserEntity> users = userRepository.findByStatus(UserStatus.HA);
		List<UserEntity> usersExpired = users.stream().filter(user -> user.getRole().getName().equals(CUSTOMER))
				.filter(user -> Objects.nonNull(user.getExpireDate()))
				.filter(user -> user.getExpireDate().isBefore(LocalDate.now())).toList();
		usersExpired.forEach(user -> {
			user.setStatus(BA);
			scheduledClaszInscriptionService.cancelClassInscriptionsFromCustomerByUserId(user.getId());
		});
		userRepository.saveAllAndFlush(usersExpired);
	}
}
