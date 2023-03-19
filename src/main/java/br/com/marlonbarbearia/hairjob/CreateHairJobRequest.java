package br.com.marlonbarbearia.hairjob;

import java.math.BigDecimal;

public record CreateHairJobRequest(
        String name,
        BigDecimal price,
        Integer durationInMinutes
) {
}
