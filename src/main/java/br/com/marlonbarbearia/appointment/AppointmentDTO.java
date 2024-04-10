package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.hairjob.HairJobDTO;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record AppointmentDTO(
        Long id,
        Long barberId,
        String barberName,
        LocalDateTime createdAt,
        Long customerId,
        String customerName,
        LocalDateTime date,
        Integer durationInMinutes,
        LocalDateTime endTime,
        Set<HairJobDTO> hairJobs,
        BigDecimal price,
        LocalDateTime updatedAt,
        AppointmentStatus status
) {
    @Override
    public LocalDateTime endTime() {
        return date.plusMinutes(durationInMinutes);
    }
}
