package com.gym.gym.service;

import static java.lang.Boolean.FALSE;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gym.gym.controllers.credentials.UpdateCredentialsRequest;
import com.gym.gym.exception.EqualPasswordException;
import com.gym.gym.exception.InvalidCredentialsException;
import com.gym.gym.repositories.user.UserEntity;

@Service
public class UpdateCredentialsService {

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	public UpdateCredentialsService(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	public void updateCredentials(Long userId, UpdateCredentialsRequest request) {
		UserEntity user = userService.findById(userId);

		if (FALSE.equals(passwordEncoder.matches(request.getOldPassword(), user.getPassword()))) {
			throw new InvalidCredentialsException();
		} else {
			if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
				throw new EqualPasswordException();
			}
			user.setPassword(passwordEncoder.encode(request.getNewPassword()));
			user.setFirstLogin(FALSE);
			userService.saveUser(user);
		}
	}

	public void updateCredentials(String email, String newPassword) {
		UserEntity user = userService.findByEmail(email);

		user.setPassword(passwordEncoder.encode(newPassword));
		userService.saveUser(user);
	}
}
