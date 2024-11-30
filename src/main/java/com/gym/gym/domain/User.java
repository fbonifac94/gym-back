package com.gym.gym.domain;

import java.time.LocalDate;

import com.gym.gym.domain.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

	private Long id;

	private String email;
	
	private UserStatus status;

	private LocalDate expireDate;

	private Person person;
}
