package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.exceptions.DateException;
import br.com.marlonbarbearia.services.AppointmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;

    @Override
    public List<AppointmentResponse> findAllAppointments() {
        return this.repository.findAllAppointments();
    }

    @Override
    public AppointmentResponse findAppointmentById(Long appointmentId) {
        Optional<AppointmentResponse> responseOptional = this.repository.findAppointmentById(appointmentId);
        return responseOptional.orElseThrow(
                () -> new ObjectNotFoundException(appointmentId, Appointment.class.getSimpleName())
        );
    }

    @Override
    public void deleteAppointmentById(Long appointmentId) {
        this.repository.deleteById(appointmentId);
    }

    @Override
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest appointment) {
        return null;
    }

    @Override
    public Boolean isAppointmentWithoutRangeOfExistingAppointments(AppointmentRequest newAppointment) {
        int newAppointmentDay = newAppointment.date().getDayOfMonth();
        int newAppointmentMonth = newAppointment.date().getMonthValue();
        int newAppointmentYear = newAppointment.date().getYear();
        long newAppointmentBarber = newAppointment.barberId();

        List<AppointmentResponse> existingAppointments = this.repository.findAppointmentsByDateAndBarberId(
                newAppointmentDay, newAppointmentMonth, newAppointmentYear, newAppointmentBarber
        );
        if(!existingAppointments.isEmpty()) {
            for (AppointmentResponse appointment : existingAppointments) {
                boolean newAppointmentStartsBeforeExisting = newAppointment.date().isBefore(appointment.date());
                boolean newAppointmentEndsBeforeExistingStarts = newAppointment
                        .date().plusMinutes(45).isBefore(appointment.date());
                boolean newAppointmentStartsAfterExistingEnds = newAppointment
                        .date().isAfter(appointment.date().plusMinutes(45));

                if (!((newAppointmentStartsBeforeExisting && newAppointmentEndsBeforeExistingStarts) ||
                        newAppointmentStartsAfterExistingEnds)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void createAppointment(AppointmentRequest request) {
        if(!isAppointmentWithoutRangeOfExistingAppointments(request)) {
            throw new DateException("The date: " + request.date() + " is already taken");
        }
        this.repository.save(
                Appointment.builder()
                        .date(request.date())
                        .createdAt(LocalDateTime.now())
                        .barberId(request.barberId())
                        .customerId(request.customerId())
                        .build());
    }

    @Override
    public List<AppointmentResponse> findAppointmentsByDate(Integer day, Integer month, Integer year) {
        return this.repository.findAppointmentsByDate(day, month, year);
    }

    @Override
    public List<AppointmentResponse> findAllAppointmentsByBarber(Long barberId) {
        return this.repository.findAllAppointmentsByBarberId(barberId);
    }

    @Override
    public List<AppointmentResponse> findAllAppointmentsByDateAndBarber(
            Integer day, Integer month, Integer year, Long barberId) {
        return this.repository
                .findAppointmentsByDateAndBarberId(day, month, year, barberId);
    }
}
