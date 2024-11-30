package com.gym.gym.controllers.passwordrecuperation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gym.gym.service.PasswordRecuperationService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/password/recuperation")
public class PasswordRecuperationController {

	private final PasswordRecuperationService passwordRecuperationService;

	@PostMapping("/code/generation/{email}")
	public ResponseEntity<Void> generateRecuperationPasswordCode(@PathVariable("email") String email) {
		passwordRecuperationService.generateRecuperationPasswordCode(email);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/code/validation/{email}")
	public ResponseEntity<Boolean> validatePasswordRecuperationCode(@PathVariable("email") String email,
			@RequestParam(value = "code", required = true) String code) {
		Boolean isValidCode = passwordRecuperationService.validateCode(email, code);
		return ResponseEntity.ok(isValidCode);
	}

	@PutMapping("/password/update/{email}")
	public ResponseEntity<Void> updatePassword(@PathVariable("email") String email,
			@RequestBody UpdateForgottenPasswordRequest request) {
		passwordRecuperationService.updatePassword(email, request.getCode(), request.getPassword());
		return ResponseEntity.ok().build();
	}

}
