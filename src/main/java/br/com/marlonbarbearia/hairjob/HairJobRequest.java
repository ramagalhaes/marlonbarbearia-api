package br.com.marlonbarbearia.hairjob;

import java.math.BigDecimal;

public record HairJobRequest(
        String name,
        BigDecimal price,
        Integer durationInMinutes
) {
}
