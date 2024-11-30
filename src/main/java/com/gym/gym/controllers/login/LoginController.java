package com.gym.gym.controllers.login;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gym.gym.auth.JwtUtil;
import com.gym.gym.repositories.user.UserEntity;

@Controller
@RequestMapping("/auth")
public class LoginController {

	private final AuthenticationManager authenticationManager;

	private JwtUtil jwtUtil;

	public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;

	}

	@ResponseBody
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginReq) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
		String token = jwtUtil.createToken((UserEntity) authentication.getPrincipal());
		LoginResponse loginRes = new LoginResponse(loginReq.getEmail(), token);

		return ResponseEntity.ok(loginRes);
	}
}
