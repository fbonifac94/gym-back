package com.gym.gym.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.gym.gym.exception.PasswordRecuperationCodeException;
import com.gym.gym.repositories.passwordrecuperation.PasswordRecuperationEntity;
import com.gym.gym.repositories.passwordrecuperation.PasswordRecuperationRepository;
import com.gym.gym.repositories.user.UserEntity;
import com.gym.gym.util.EmailUtils;
import com.gym.gym.util.Utils;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PasswordRecuperationService {

	private final PasswordRecuperationRepository passwordRecuperationRepository;

	private final UpdateCredentialsService updateCredentialsService;

	private final UserService userService;

	private final EmailUtils emailUtils;

	@Transactional
	public void generateRecuperationPasswordCode(String email) {
		UserEntity userEntity = userService.findByEmail(email);
		passwordRecuperationRepository.deletePasswordsRecuperationCodesByEmail(email);
		PasswordRecuperationEntity passwordRecuperationEntity = new PasswordRecuperationEntity(
				Utils.generateRandomAlphanumericString(5), email, LocalDateTime.now());
		passwordRecuperationRepository.saveAndFlush(passwordRecuperationEntity);
		sendRegistrationEmail(email, userEntity.getPerson().getFirstName(), userEntity.getPerson().getLastName(),
				passwordRecuperationEntity.getCode());
	}

	public Boolean validateCode(String email, String code) {
		List<PasswordRecuperationEntity> passwordRecuperationEntityList = passwordRecuperationRepository
				.findByEmail(email);

		PasswordRecuperationEntity lastPasswordRecuperationEntity = passwordRecuperationEntityList.stream().findFirst()
				.orElseThrow(() -> new PasswordRecuperationCodeException(String
						.format("No se encontraron códigos de recuperación de contraseña asociados a %s", email)));

		return lastPasswordRecuperationEntity.getCode().equals(code);
	}

	@Transactional
	public void updatePassword(String email, String code, String newPassword) {
		Boolean isValidCode = validateCode(email, code);

		if (isValidCode) {
			updateCredentialsService.updateCredentials(email, newPassword);
			passwordRecuperationRepository.deletePasswordsRecuperationCodesByEmail(email);
		} else {
			throw new PasswordRecuperationCodeException(String
					.format("Ocurrió un error en el proceso de actualización de contraseña para el email %s", email));
		}
	}

	private void sendRegistrationEmail(String email, String firstName, String lastName, String code) {
		Context context = new Context();
		context.setVariable("name", new StringBuilder(firstName).append(" ").append(lastName).toString());
		context.setVariable("code", code);

		emailUtils.sendEmail("facu.bonifacich@gmail.com", "Recuperación de contraseña Phanteon Fitness", context,
				"password-recuperation-template");
	}
}
