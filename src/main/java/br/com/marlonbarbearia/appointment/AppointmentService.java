package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.barbershop.barber.BarberService;
import br.com.marlonbarbearia.customer.CustomerService;
import br.com.marlonbarbearia.exceptions.DateException;
import br.com.marlonbarbearia.exceptions.NotAllowedException;
import br.com.marlonbarbearia.barbershop.hairjob.HairJob;
import br.com.marlonbarbearia.barbershop.hairjob.HairJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.marlonbarbearia.appointment.AppointmentStatus.CREATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentService {

    private final AppointmentRepository repository;
    private final BarberService barberService;
    private final CustomerService customerService;
    private final HairJobService hairJobService;
    private final Clock clock;

    public List<AppointmentDTO> findAllAppointments() {
        return repository.findAllAppointments()
                .stream()
                .map(AppointmentMapper::appointmentToResponse)
                .toList();
    }

    public AppointmentDTO findAppointmentDTOById(Long appointmentId) {
       return AppointmentMapper.appointmentToResponse(findAppointmentById(appointmentId));
    }

    private Appointment findAppointmentById(Long appointmentId) {
        return repository.findAppointmentById(appointmentId)
                .orElseThrow(() -> new ObjectNotFoundException(appointmentId, Appointment.class.getSimpleName()));
    }

    public void deleteAppointmentById(Long appointmentId) {
        repository.deleteById(appointmentId);
    }

    public void updateAppointment(Long id, UpdateAppointmentRequest request) {
        Appointment existingAppointment = findAppointmentById(id);
        existingAppointment.setDate(request.date());
        existingAppointment.setHairJobs(
                request.hairJobs().stream()
                        .map(hairJobService::findHairJobById)
                        .collect(Collectors.toSet())
        );
        if (appointmentHasTimeConflict(existingAppointment)) {
            throw new DateException("The date: [" + request.date() + "] is already taken");
        }
        existingAppointment.setUpdatedAt(LocalDateTime.now(clock));
        repository.save(existingAppointment);
    }

    public boolean appointmentHasTimeConflict(Appointment newAppointment) {
        int newAppointmentDay = newAppointment.getDate().getDayOfMonth();
        int newAppointmentMonth = newAppointment.getDate().getMonthValue();
        int newAppointmentYear = newAppointment.getDate().getYear();
        int totalDurationInMinutes = newAppointment.getHairJobs().stream()
                .reduce(0, (acc, curr) -> Integer.sum(acc, curr.getDurationInMinutes()), Integer::sum);

        List<Appointment> existingAppointments = repository.findAppointmentsByDateAndBarberId(
                newAppointmentDay, newAppointmentMonth, newAppointmentYear, newAppointment.getBarber().getId()
        );

        if (!existingAppointments.isEmpty()) {
            for (Appointment appointment : existingAppointments) {

                if (appointment.equals(newAppointment)) { continue; }

                boolean newAppointmentStartsBeforeExisting = newAppointment.getDate().isBefore(appointment.getDate());
                boolean newAppointmentEndsBeforeExistingStarts = newAppointment.getDate()
                        .plusMinutes(totalDurationInMinutes).isBefore(appointment.getDate());
                boolean newAppointmentStartsAfterExistingEnds = newAppointment.getDate()
                        .isAfter(appointment.getDate().plusMinutes(totalDurationInMinutes));

                if (!((newAppointmentStartsBeforeExisting && newAppointmentEndsBeforeExistingStarts) ||
                        newAppointmentStartsAfterExistingEnds)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void createAppointment(CreateAppointmentRequest request) {
        Appointment appointment = appointmentFromRequest(request);
        if (!customerService.isAccountConfirmed(appointment.getCustomer())) {
            throw new NotAllowedException("Account: [" + appointment.getCustomer().getId() +"] is not confirmed");
        }
        if (appointmentHasTimeConflict(appointment)) {
            throw new DateException("The date: [" + request.date() + "] is already taken");
        }
        repository.save(appointment);
    }

    public List<AppointmentDTO> findAppointmentsByDate(Integer day, Integer month, Integer year) {
        return repository.findAppointmentsByDate(day, month, year)
                .stream()
                .map(AppointmentMapper::appointmentToResponse)
                .toList();
    }

    public List<AppointmentDTO> findAllAppointmentsByBarber(Long barberId) {
        return repository.findAllAppointmentsByBarberId(barberId)
                .stream()
                .map(AppointmentMapper::appointmentToResponse)
                .toList();
    }

    public List<AppointmentDTO> findAllAppointmentsByDateAndBarber(
            Integer day, Integer month, Integer year, Long barberId) {
        return repository
                .findAppointmentsByDateAndBarberId(day, month, year, barberId)
                .stream()
                .map(AppointmentMapper::appointmentToResponse)
                .toList();
    }

    private Appointment appointmentFromRequest(CreateAppointmentRequest request) {
        Set<HairJob> hairJobs = request.hairJobs()
                .stream()
                .map(hairJobService::findHairJobById)
                .collect(Collectors.toSet());
        BigDecimal totalPrice = hairJobs
                .stream()
                .reduce(BigDecimal.ZERO, (acc, curr) -> acc.add(curr.getPrice()), BigDecimal::add);
        int totalDurationInMinutes = hairJobs.stream()
                .reduce(0, (acc, curr) -> Integer.sum(acc, curr.getDurationInMinutes()), Integer::sum);

        return Appointment.builder()
                .date(request.date())
                .createdAt(LocalDateTime.now(clock))
                .barber(barberService.findBarberEntityById(request.barberId()))
                .customer(customerService.findCustomerEntityById(request.customerId()))
                .hairJobs(hairJobs)
                .price(totalPrice)
                .durationInMinutes(totalDurationInMinutes)
                .status(CREATED.getCode())
                .build();

    }
}
