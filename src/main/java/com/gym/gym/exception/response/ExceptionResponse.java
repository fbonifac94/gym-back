package com.gym.gym.exception.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionResponse {

	private Integer code;

	private String errorMessage;

	public ExceptionResponse(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
