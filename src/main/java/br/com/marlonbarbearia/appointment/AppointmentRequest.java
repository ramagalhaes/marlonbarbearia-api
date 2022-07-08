package br.com.marlonbarbearia.appointment;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AppointmentRequest(
        Long barberId,
        Long customerId,
        LocalDateTime date
) {
}
