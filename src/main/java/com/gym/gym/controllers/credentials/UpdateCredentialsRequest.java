package com.gym.gym.controllers.credentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateCredentialsRequest {

	private String oldPassword;

	private String newPassword;
}
