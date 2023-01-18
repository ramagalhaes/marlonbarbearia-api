package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.hairjob.HairJobResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record AppointmentResponse(
        Long id,
        Long barberId,
        String barberName,
        LocalDateTime createdAt,
        Long customerId,
        String customerName,
        LocalDateTime date,
        Integer durationInMinutes,
        LocalDateTime endTime,
        Set<HairJobResponse> hairJobs,
        BigDecimal price,
        LocalDateTime updatedAt
) {
    @Override
    public LocalDateTime endTime() {
        return date.plusMinutes(durationInMinutes);
    }
}
