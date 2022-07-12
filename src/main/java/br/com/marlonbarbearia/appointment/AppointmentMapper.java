package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.hairjob.HairJob;
import br.com.marlonbarbearia.hairjob.HairJobResponse;

import java.util.stream.Collectors;

public class AppointmentMapper {

    public static AppointmentResponse appointmentToResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .barberId(appointment.getBarber().getId())
                .barberName(appointment.getBarber().getName())
                .createdAt(appointment.getCreatedAt())
                .customerId(appointment.getCustomer().getId())
                .customerName(appointment.getCustomer().getName())
                .date(appointment.getDate())
                .hairJobs(appointment.getHairJobs().stream()
                        .map(h -> HairJobResponse.builder()
                                .id(h.getId())
                                .durationInSeconds(h.getDurationInSeconds())
                                .name(h.getName())
                                .price(h.getPrice())
                                .build()
                        ).collect(Collectors.toSet()))
                .build();
    }

}
