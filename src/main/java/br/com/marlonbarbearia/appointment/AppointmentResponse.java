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
        Integer durationInSeconds,
        Set<HairJobResponse> hairJobs,
        BigDecimal price
) {
    @Override
    public BigDecimal price() {
        BigDecimal total = new BigDecimal(0);
        for(HairJobResponse h : hairJobs) {
            total = total.add(h.price());
        }
        return total;
    }

    @Override
    public Integer durationInSeconds() {
        int total = 0;
        for(HairJobResponse h : hairJobs) {
            total += h.durationInSeconds();
        }
        return total;
    }
}
