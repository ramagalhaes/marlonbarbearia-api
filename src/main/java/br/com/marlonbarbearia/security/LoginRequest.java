package br.com.marlonbarbearia.security;

import lombok.Builder;

@Builder
public record LoginRequest(
        String username,
        String password
) {
}
