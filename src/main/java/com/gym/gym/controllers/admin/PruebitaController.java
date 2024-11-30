package com.gym.gym.controllers.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gym.gym.auth.JwtUserData;

@Controller
@RequestMapping("/prueba")
public class PruebitaController {

	@GetMapping
	public ResponseEntity<Void> prueba(@AuthenticationPrincipal JwtUserData jwtUserData) {
	    System.out.println("Hola" + jwtUserData.getUsername());
	    return ResponseEntity.ok().build();
	}
}
