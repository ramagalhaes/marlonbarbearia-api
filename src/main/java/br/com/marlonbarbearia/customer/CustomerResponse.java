package br.com.marlonbarbearia.customer;

import lombok.Builder;

@Builder
public record CustomerResponse(
        Long id,
        String name,
        String lastName,
        String phoneNumber
) {
}
