package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.barber.Barber;
import br.com.marlonbarbearia.customer.Customer;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AppointmentResponse(
        Long id,
        Long barberId,
        LocalDateTime createdAt,
        Long customerId,
        LocalDateTime date
) {
}
