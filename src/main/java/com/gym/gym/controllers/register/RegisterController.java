package com.gym.gym.controllers.register;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gym.gym.service.RegisterService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

	private final RegisterService userService;

	public RegisterController(RegisterService userService) {
		this.userService = userService;
	}

	@PostMapping("/admin")
	public ResponseEntity<Void> registerAdmin(@Valid @RequestBody RegisterRequest registerReq) {
		this.userService.registerAdmin(registerReq);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/teacher")
	public ResponseEntity<Void> registerTeacher(@Valid @RequestBody RegisterRequest registerReq) {
		this.userService.registerTeacher(registerReq);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/customer")
	public ResponseEntity<Void> registerCustomer(@Valid @RequestBody RegisterRequest registerReq) {
		this.userService.registerCustomer(registerReq);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
