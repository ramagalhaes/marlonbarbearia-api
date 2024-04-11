package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.barbershop.hairjob.HairJobDTO;

import java.util.stream.Collectors;

public abstract class AppointmentMapper {

    private AppointmentMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static AppointmentDTO appointmentToResponse(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .barberId(appointment.getBarber().getId())
                .barberName(appointment.getBarber().getName() + " " +appointment.getBarber().getLastName())
                .createdAt(appointment.getCreatedAt())
                .customerId(appointment.getCustomer().getId())
                .customerName(appointment.getCustomer().getName() + " " + appointment.getCustomer().getLastName())
                .date(appointment.getDate())
                .durationInMinutes(appointment.getDurationInMinutes())
                .endTime(appointment.getDate().plusMinutes(appointment.getDurationInMinutes()))
                .hairJobs(appointment.getHairJobs().stream()
                        .map(h -> HairJobDTO.builder()
                                .id(h.getId())
                                .durationInMinutes(h.getDurationInMinutes())
                                .name(h.getName())
                                .price(h.getPrice())
                                .build()
                        ).collect(Collectors.toSet()))
                .price(appointment.getPrice())
                .updatedAt(appointment.getUpdatedAt())
                .status(appointment.getStatus())
                .build();
    }
}
