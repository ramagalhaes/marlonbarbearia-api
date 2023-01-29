package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.barber.Barber;
import br.com.marlonbarbearia.barber.BarberService;
import br.com.marlonbarbearia.customer.Customer;
import br.com.marlonbarbearia.customer.CustomerService;
import br.com.marlonbarbearia.exceptions.DateException;
import br.com.marlonbarbearia.hairjob.HairJob;
import br.com.marlonbarbearia.hairjob.HairJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final BarberService barberService;

    private final CustomerService customerService;
    private final HairJobService hairJobService;

    private final Clock clock;

    @Override
    public List<AppointmentDTO> findAllAppointments() {
        return repository.findAllAppointments()
                .stream()
                .map(AppointmentMapper::appointmentToResponse)
                .collect(toList());
    }

    @Override
    public AppointmentDTO findAppointmentById(Long appointmentId) {
        Optional<Appointment> responseOptional = repository.findAppointmentById(appointmentId);
        if(responseOptional.isEmpty()) {
            throw new ObjectNotFoundException(appointmentId, Appointment.class.getSimpleName());
        }
       return AppointmentMapper.appointmentToResponse(responseOptional.get());
    }

    @Override
    public void deleteAppointmentById(Long appointmentId) {
        repository.deleteById(appointmentId);
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentRequest appointment) {
        return null;
    }

    @Override
    public Boolean appointmentHasTimeConflict(LocalDateTime appointmentDate, Long barberId) {
        int newAppointmentDay = appointmentDate.getDayOfMonth();
        int newAppointmentMonth = appointmentDate.getMonthValue();
        int newAppointmentYear = appointmentDate.getYear();

        List<Appointment> existingAppointments = repository.findAppointmentsByDateAndBarberId(
                newAppointmentDay, newAppointmentMonth, newAppointmentYear, barberId
        );
        if(!existingAppointments.isEmpty()) {
            for (Appointment appointment : existingAppointments) {
                boolean newAppointmentStartsBeforeExisting = appointmentDate.isBefore(appointment.getDate());
                boolean newAppointmentEndsBeforeExistingStarts = appointmentDate
                        .plusMinutes(45).isBefore(appointment.getDate());
                boolean newAppointmentStartsAfterExistingEnds = appointmentDate
                        .isAfter(appointment.getDate().plusMinutes(45));

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
        if(FALSE.equals(appointmentHasTimeConflict(request.date(), request.barberId()))) {
            throw new DateException("The date: [" + request.date() + "] is already taken");
        }
        Barber barber = barberService.findBarberEntityById(request.barberId());
        Customer customer = customerService.findCustomerEntityById(request.customerId());
        Set<HairJob> hairJobs = request.hairJobs()
                .stream()
                .map(hairJobService::findHairJobEntityById)
                .collect(Collectors.toSet());
        BigDecimal totalPrice = hairJobs
                .stream()
                .reduce(BigDecimal.ZERO, (acc, curr) -> acc.add(curr.getPrice()), BigDecimal::add);
        int totalDurationInMinutes = hairJobs.stream()
                .reduce(0, (acc, curr) -> Integer.sum(acc, curr.getDurationInMinutes()), Integer::sum);

        repository.save(
                Appointment.builder()
                        .date(request.date())
                        .createdAt(LocalDateTime.now(clock))
                        .barber(barber)
                        .customer(customer)
                        .hairJobs(hairJobs)
                        .price(totalPrice)
                        .durationInMinutes(totalDurationInMinutes)
                        .build());
    }

    @Override
    public List<AppointmentDTO> findAppointmentsByDate(Integer day, Integer month, Integer year) {
        return repository.findAppointmentsByDate(day, month, year)
                .stream()
                .map(AppointmentMapper::appointmentToResponse)
                .collect(toList());
    }

    @Override
    public List<AppointmentDTO> findAllAppointmentsByBarber(Long barberId) {
        return repository.findAllAppointmentsByBarberId(barberId)
                .stream()
                .map(AppointmentMapper::appointmentToResponse)
                .collect(toList());
    }

    @Override
    public List<AppointmentDTO> findAllAppointmentsByDateAndBarber(
            Integer day, Integer month, Integer year, Long barberId) {
        return repository
                .findAppointmentsByDateAndBarberId(day, month, year, barberId)
                .stream()
                .map(AppointmentMapper::appointmentToResponse)
                .collect(toList());
    }
}
