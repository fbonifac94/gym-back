package com.gym.gym.exception;

public class MailInUseException extends RuntimeException {

	private static final long serialVersionUID = 6134495992450621833L;

	public MailInUseException(String email) {
		super(String.format("El mail %s se encuentra en uso.", email));
	}
}
