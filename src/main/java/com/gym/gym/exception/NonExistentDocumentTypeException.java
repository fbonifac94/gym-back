package com.gym.gym.exception;

public class NonExistentDocumentTypeException extends RuntimeException {

	private static final long serialVersionUID = 4412406278593123602L;

	public NonExistentDocumentTypeException(String documentType) {
		super(String.format("El tipo de documento %s no es válido.", documentType));
	}
}
