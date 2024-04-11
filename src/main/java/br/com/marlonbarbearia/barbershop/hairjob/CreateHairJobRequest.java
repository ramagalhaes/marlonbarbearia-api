package br.com.marlonbarbearia.barbershop.hairjob;

import java.math.BigDecimal;

public record CreateHairJobRequest(
        String name,
        BigDecimal price,
        Integer durationInMinutes
) {
}
