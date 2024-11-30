package com.gym.gym.controllers.credentials;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gym.gym.auth.JwtUserData;
import com.gym.gym.service.UpdateCredentialsService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/credentials")
public class UpdateCredentialsController {

	private final UpdateCredentialsService updateCredentialsService;

	public UpdateCredentialsController(UpdateCredentialsService updateCredentialsService) {
		this.updateCredentialsService = updateCredentialsService;
	}

	@PutMapping("/update")
	public ResponseEntity<Void> updateCredentials(@AuthenticationPrincipal JwtUserData jwtUserData,
			@Valid @RequestBody UpdateCredentialsRequest request) {
		this.updateCredentialsService.updateCredentials(jwtUserData.getUserId(), request);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
