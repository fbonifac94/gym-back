package com.gym.gym.exception;

import lombok.Getter;

@Getter
public class EqualPasswordException extends RuntimeException {

	private static final long serialVersionUID = -286408080652818307L;

	private String message;

	private Integer code;

	public EqualPasswordException() {
		this.message = "La nueva contraseña coincide con la anterior.";
		this.code = 101;
	}
}
