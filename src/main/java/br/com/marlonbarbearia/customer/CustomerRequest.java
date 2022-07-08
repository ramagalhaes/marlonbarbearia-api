package br.com.marlonbarbearia.customer;

import lombok.Builder;

@Builder
public record CustomerRequest(
        String name,
        String phoneNumber
) {
}
