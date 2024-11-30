package com.gym.gym.exception;

public class DocumentInUseException extends RuntimeException {

	private static final long serialVersionUID = 2427638932102303471L;

	public DocumentInUseException(String documentType, String documentNumber) {
		super(String.format("El documento %s %s, se encuentra en uso", documentType, documentNumber));
	}
}
