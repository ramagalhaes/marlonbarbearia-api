package br.com.marlonbarbearia.exceptions;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError error = StandardError.builder()
				.httpStatus(NOT_FOUND.value())
				.message(e.getMessage())
				.path(request.getRequestURI())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(NOT_FOUND).body(error);
	}

	@ExceptionHandler(ObjectAlreadyExistsException.class)
	public ResponseEntity<StandardError> objectAlreadyExists(ObjectAlreadyExistsException e, HttpServletRequest request) {
		StandardError error = StandardError.builder()
				.httpStatus(CONFLICT.value())
				.message(e.getMessage())
				.path(request.getRequestURI())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(CONFLICT).body(error);
	}

	@ExceptionHandler(DateException.class)
	public ResponseEntity<StandardError> dateException(DateException e, HttpServletRequest request) {
		StandardError error = StandardError.builder()
				.httpStatus(CONFLICT.value())
				.message(e.getMessage())
				.path(request.getRequestURI())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(CONFLICT).body(error);
	}

	@ExceptionHandler(NotAllowedException.class)
	public ResponseEntity<StandardError> notAllowedException(NotAllowedException e, HttpServletRequest request) {
		StandardError error = StandardError.builder()
				.httpStatus(FORBIDDEN.value())
				.message(e.getMessage())
				.path(request.getRequestURI())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(FORBIDDEN).body(error);
	}

	@ExceptionHandler(ConfirmationTokenException.class)
	public ResponseEntity<StandardError> notAllowedException(ConfirmationTokenException e, HttpServletRequest request) {
		StandardError error = StandardError.builder()
				.httpStatus(BAD_REQUEST.value())
				.message(e.getMessage())
				.path(request.getRequestURI())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(BAD_REQUEST).body(error);
	}

}
