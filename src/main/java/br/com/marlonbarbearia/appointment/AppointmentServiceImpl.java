package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.barber.Barber;
import br.com.marlonbarbearia.customer.Customer;
import br.com.marlonbarbearia.exceptions.DateException;
import br.com.marlonbarbearia.barber.BarberService;
import br.com.marlonbarbearia.customer.CustomerService;
import br.com.marlonbarbearia.hairjob.HairJob;
import br.com.marlonbarbearia.hairjob.HairJobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final BarberService barberService;

    private final CustomerService customerService;
    private final HairJobService hairJobService;

    @Override
    public List<AppointmentResponse> findAllAppointments() {
        return this.repository.findAllAppointments()
                .stream()
                .map(a -> AppointmentMapper.appointmentToResponse(a))
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponse findAppointmentById(Long appointmentId) {
        Optional<Appointment> responseOptional = this.repository.findAppointmentById(appointmentId);
        if(responseOptional.isEmpty()) {
            throw new ObjectNotFoundException(appointmentId, Appointment.class.getSimpleName());
        }
       return AppointmentMapper.appointmentToResponse(responseOptional.get());
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

        List<Appointment> existingAppointments = this.repository.findAppointmentsByDateAndBarberId(
                newAppointmentDay, newAppointmentMonth, newAppointmentYear, newAppointmentBarber
        );
        if(!existingAppointments.isEmpty()) {
            for (Appointment appointment : existingAppointments) {
                boolean newAppointmentStartsBeforeExisting = newAppointment.date().isBefore(appointment.getDate());
                boolean newAppointmentEndsBeforeExistingStarts = newAppointment
                        .date().plusMinutes(45).isBefore(appointment.getDate());
                boolean newAppointmentStartsAfterExistingEnds = newAppointment
                        .date().isAfter(appointment.getDate().plusMinutes(45));

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
            throw new DateException("The date: [" + request.date() + "] is already taken");
        }
        Barber barber = this.barberService.findBarberEntityById(request.barberId());
        Customer customer = this.customerService.findCustomerEntityById(request.customerId());
        Set<HairJob> hairJobs = request.hairJobs()
                .stream()
                .map(id -> this.hairJobService.findHairJobEntityById(id))
                .collect(Collectors.toSet());

        this.repository.save(
                Appointment.builder()
                        .date(request.date())
                        .createdAt(LocalDateTime.now())
                        .barber(barber)
                        .customer(customer)
                        .hairJobs(hairJobs)
                        .build());
    }

    @Override
    public List<AppointmentResponse> findAppointmentsByDate(Integer day, Integer month, Integer year) {
        return this.repository.findAppointmentsByDate(day, month, year).stream()
                .map(a -> AppointmentMapper.appointmentToResponse(a))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponse> findAllAppointmentsByBarber(Long barberId) {
        return this.repository.findAllAppointmentsByBarberId(barberId).stream()
                .map(a -> AppointmentMapper.appointmentToResponse(a))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponse> findAllAppointmentsByDateAndBarber(
            Integer day, Integer month, Integer year, Long barberId) {
        return this.repository
                .findAppointmentsByDateAndBarberId(day, month, year, barberId).stream()
                .map(a -> AppointmentMapper.appointmentToResponse(a))
                .collect(Collectors.toList());
    }
}
