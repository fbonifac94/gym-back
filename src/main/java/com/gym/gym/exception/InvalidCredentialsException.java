package com.gym.gym.exception;

public class InvalidCredentialsException extends RuntimeException{

	private static final long serialVersionUID = 1493684565378453719L;

	public InvalidCredentialsException() {
		super("Credenciales inválidas.");
	}
}
