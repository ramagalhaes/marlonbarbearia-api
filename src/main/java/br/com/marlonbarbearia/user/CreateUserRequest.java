package br.com.marlonbarbearia.user;

public record CreateUserRequest(
        String name,
        String lastName,
        String phoneNumber,
        String password
) {
}
