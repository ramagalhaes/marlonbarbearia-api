package br.com.marlonbarbearia.customer;

import br.com.marlonbarbearia.enums.UserType;
import lombok.Builder;

import java.util.Set;

@Builder
public record CustomerResponse(
        Long id,
        String name,
        String phoneNumber,
        Set<UserType> roles
) {
}
