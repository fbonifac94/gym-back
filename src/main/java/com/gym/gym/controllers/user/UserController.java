package com.gym.gym.controllers.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gym.gym.auth.JwtUserData;
import com.gym.gym.domain.User;
import com.gym.gym.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) {
		User user = this.userService.findByUserId(id);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@PutMapping("/user")
	public ResponseEntity<User> updatePerson(@AuthenticationPrincipal JwtUserData jwtUserData,
			@Valid @RequestBody UpdatePersonRequest request) {
		this.userService.updateUserByUserId(jwtUserData.getUserId(), request);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
