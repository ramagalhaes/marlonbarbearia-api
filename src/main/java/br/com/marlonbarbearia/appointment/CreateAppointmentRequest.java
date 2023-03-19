package br.com.marlonbarbearia.appointment;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record CreateAppointmentRequest(
        Long barberId,
        Long customerId,
        LocalDateTime date,
        Set<Long>hairJobs
) {
}
