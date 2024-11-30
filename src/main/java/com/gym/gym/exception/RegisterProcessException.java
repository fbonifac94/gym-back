package com.gym.gym.exception;

import lombok.Getter;

@Getter
public class RegisterProcessException extends RuntimeException {

	private static final long serialVersionUID = -8967941023434936816L;

	private String message;

	private Integer code;

	public RegisterProcessException(String message) {
		this.code = 100;
		this.message = message;
	}

}
