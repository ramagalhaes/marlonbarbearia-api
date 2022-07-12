package br.com.marlonbarbearia.configs;


import org.springframework.beans.factory.annotation.Value;

public record AppointmentsConfig(
        @Value("${marlonbarbearia.appointment.duration}")
         Integer appointmentDuration
) {

}
