package br.com.marlonbarbearia.user;

import lombok.Builder;

@Builder
public record CreateUserRequest(
        String name,
        String lastName,
        String phoneNumber,
        String password
) {
}
