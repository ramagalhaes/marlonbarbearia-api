package br.com.marlonbarbearia.barbershop.barber;

import lombok.Builder;

@Builder
public record BarberDTO(
        Long id,
        String name,
        String lastName,
        String phoneNumber
) {
}
