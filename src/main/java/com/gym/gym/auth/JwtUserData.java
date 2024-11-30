package com.gym.gym.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtUserData {

	private String username;

	private Long userId;

	private String firstName;

	private String lastName;

	private String role;
}
