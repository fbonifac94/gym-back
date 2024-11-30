package com.gym.gym.exception.response;

import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConstraintsErrorsResponse {

	private String field;

	private String message;

	public ConstraintsErrorsResponse(FieldError error) {
		this.field = error.getField();
		this.message = error.getDefaultMessage();
	}
}
