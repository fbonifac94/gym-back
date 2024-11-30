package com.gym.gym.domain.enums;

public enum UserStatus {
	HA("Habilitado"), BA("Inhabilitado");

	private String status;

	private UserStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
