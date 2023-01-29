package br.com.marlonbarbearia.hairjob;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record HairJobDTO(
        Long id,
        String name,
        BigDecimal price,
        Integer durationInMinutes
) {
}
