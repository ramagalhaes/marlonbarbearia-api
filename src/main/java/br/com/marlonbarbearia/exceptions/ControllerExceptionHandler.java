package br.com.marlonbarbearia.exceptions;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError error = StandardError.builder()
				.httpStatus(HttpStatus.NOT_FOUND.value())
				.message(e.getMessage())
				.path(request.getRequestURI())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(ObjectAlreadyExistsException.class)
	public ResponseEntity<StandardError> objectAlreadyExists(ObjectAlreadyExistsException e, HttpServletRequest request) {
		StandardError error = StandardError.builder()
				.httpStatus(HttpStatus.CONFLICT.value())
				.message(e.getMessage())
				.path(request.getRequestURI())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

	@ExceptionHandler(DateException.class)
	public ResponseEntity<StandardError> dateException(DateException e, HttpServletRequest request) {
		StandardError error = StandardError.builder()
				.httpStatus(HttpStatus.CONFLICT.value())
				.message(e.getMessage())
				.path(request.getRequestURI())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

}
