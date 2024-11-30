package com.gym.gym.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClaszInConflictException extends RuntimeException {

	private static final long serialVersionUID = -4389583370413915646L;

	private String message;

	private Integer code;

	public ClaszInConflictException(String message) {
		this.code = 102;
		this.message = message;
	}
}
