package br.com.marlonbarbearia.barber;

import lombok.Builder;

@Builder
public record BarberRequest(
        String name,
        String phoneNumber
) {
}
