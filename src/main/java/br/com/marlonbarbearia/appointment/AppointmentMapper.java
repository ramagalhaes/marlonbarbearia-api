package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.hairjob.HairJobResponse;

import java.util.stream.Collectors;

public class AppointmentMapper {

    public static AppointmentResponse appointmentToResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .barberId(appointment.getBarber().getId())
                .barberName(appointment.getBarber().getName() + appointment.getBarber().getLastName())
                .createdAt(appointment.getCreatedAt())
                .customerId(appointment.getCustomer().getId())
                .customerName(appointment.getCustomer().getName() + appointment.getCustomer().getLastName())
                .date(appointment.getDate())
                .durationInMinutes(appointment.getDurationInMinutes())
                .endTime(appointment.getDate().plusMinutes(appointment.getDurationInMinutes()))
                .hairJobs(appointment.getHairJobs().stream()
                        .map(h -> HairJobResponse.builder()
                                .id(h.getId())
                                .durationInMinutes(h.getDurationInMinutes())
                                .name(h.getName())
                                .price(h.getPrice())
                                .build()
                        ).collect(Collectors.toSet()))
                .price(appointment.getPrice())
                .build();
    }

}
