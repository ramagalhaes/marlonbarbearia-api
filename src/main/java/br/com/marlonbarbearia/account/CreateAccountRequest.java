package br.com.marlonbarbearia.account;

import lombok.Builder;

@Builder
public record CreateAccountRequest(
        String name,
        String lastName,
        String phoneNumber,
        String password
) {
}
