package com.gym.gym.controllers.register;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gym.gym.service.UserService;

@Controller
@RequestMapping("/user/status")
public class UpdateUserStatusController {

	private final UserService userService;

	public UpdateUserStatusController(UserService userService) {
		this.userService = userService;
	}

	@PutMapping("/unsuscribe/{userId}")
	public ResponseEntity<Void> unsuscribeUser(@PathVariable("userId") Long userId) {
		this.userService.unsuscribeUser(userId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping("/suscribe/{userId}")
	public ResponseEntity<Void> suscribeUser(@PathVariable("userId") Long userId) {
		this.userService.suscribeUser(userId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
