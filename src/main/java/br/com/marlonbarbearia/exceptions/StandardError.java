package br.com.marlonbarbearia.exceptions;


import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record StandardError(
		 LocalDateTime timestamp,
		 Integer httpStatus,
		 String message,
		 String path
) {
}
