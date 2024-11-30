package com.gym.gym.exception.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gym.gym.exception.ClaszInConflictException;
import com.gym.gym.exception.DocumentInUseException;
import com.gym.gym.exception.EmailSendingException;
import com.gym.gym.exception.EqualPasswordException;
import com.gym.gym.exception.MailInUseException;
import com.gym.gym.exception.NonExistentDocumentTypeException;
import com.gym.gym.exception.RegisterProcessException;
import com.gym.gym.exception.response.ConstraintsErrorsResponse;
import com.gym.gym.exception.response.ExceptionResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(value = { MailInUseException.class, NonExistentDocumentTypeException.class,
			DocumentInUseException.class })
	public ResponseEntity<ExceptionResponse> handleMailInUseException(HttpServletRequest req, RuntimeException e)
			throws Exception {
		return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
	}

	@ExceptionHandler(value = RegisterProcessException.class)
	public ResponseEntity<ExceptionResponse> handleRegisterProcessException(HttpServletRequest req,
			RegisterProcessException e) throws Exception {
		return ResponseEntity.badRequest().body(new ExceptionResponse(e.getCode(), e.getMessage()));
	}

	@ExceptionHandler(value = ClaszInConflictException.class)
	public ResponseEntity<ExceptionResponse> handleClaszInConflictException(HttpServletRequest req,
			ClaszInConflictException e) throws Exception {
		return ResponseEntity.badRequest().body(new ExceptionResponse(e.getCode(), e.getMessage()));
	}

	@ExceptionHandler(value = EqualPasswordException.class)
	public ResponseEntity<ExceptionResponse> handleEqualPasswordException(HttpServletRequest req,
			EqualPasswordException e) throws Exception {
		return ResponseEntity.badRequest().body(new ExceptionResponse(e.getCode(), e.getMessage()));
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<List<ConstraintsErrorsResponse>> handleMethodArgumentNotValidException(HttpServletRequest req,
			MethodArgumentNotValidException e) throws Exception {
		return ResponseEntity.badRequest()
				.body(e.getFieldErrors().stream().map(ConstraintsErrorsResponse::new).collect(Collectors.toList()));
	}

	@ExceptionHandler(value = { EntityNotFoundException.class })
	public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(HttpServletRequest req,
			EntityNotFoundException e) throws Exception {
		return ResponseEntity.internalServerError().body(new ExceptionResponse(e.getMessage()));
	}

	@ExceptionHandler(value = { EmailSendingException.class })
	public ResponseEntity<ExceptionResponse> handleEmailSendingException(HttpServletRequest req,
			EmailSendingException e) throws Exception {
		return ResponseEntity.internalServerError().body(new ExceptionResponse(e.getMessage()));
	}
}
