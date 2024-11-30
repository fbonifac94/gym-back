package com.gym.gym.controllers.login;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {

	private String email;
	
	private String token;
}
