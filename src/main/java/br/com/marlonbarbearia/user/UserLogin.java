package br.com.marlonbarbearia.user;

import lombok.Builder;

@Builder
public record UserLogin(
        String phoneNumber,
        String password
) {
}
