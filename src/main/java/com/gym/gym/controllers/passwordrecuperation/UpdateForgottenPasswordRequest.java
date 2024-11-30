package com.gym.gym.controllers.passwordrecuperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateForgottenPasswordRequest {

	private String code;

	private String password;
}
