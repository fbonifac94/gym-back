package com.gym.gym.exception;

public class EmailSendingException extends RuntimeException {

	private static final long serialVersionUID = -2986486991430326280L;

	public EmailSendingException(String message) {
		super(String.format("Ocurrió un error en el proceso de envío de mail: %s", message));
	}
}
