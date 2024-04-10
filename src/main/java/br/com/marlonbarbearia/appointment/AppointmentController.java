package br.com.marlonbarbearia.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    ResponseEntity<Void> createAppointment(@RequestBody CreateAppointmentRequest createAppointmentRequest) {
        service.createAppointment(createAppointmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{appointmentId}")
    ResponseEntity<Void> deleteAppointment(@PathVariable("appointmentId") long appointmentId) {
        service.deleteAppointmentById(appointmentId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('BARBER')")
    @GetMapping
    ResponseEntity<List<AppointmentDTO>> findAllAppointments() {
        List<AppointmentDTO> appointments = service.findAllAppointments();
        return ResponseEntity.ok().body(appointments);
    }

    @GetMapping("/{appointmentId}")
    ResponseEntity<AppointmentDTO> findAppointmentById(@PathVariable("appointmentId") long appointmentId) {
        AppointmentDTO appointment = service.findAppointmentDTOById(appointmentId);
        return ResponseEntity.ok().body(appointment);
    }

    @GetMapping("/barber")
    ResponseEntity<List<AppointmentDTO>> findAllAppointmentsByBarber(@RequestParam("barber-id") long barberId) {
        List<AppointmentDTO> response = service.findAllAppointmentsByBarber(barberId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/barber/{barberId}/date")
    ResponseEntity<List<AppointmentDTO>> findAppointmentsByDateAndBarber(@PathVariable("barberId") long barberId,
                                                                         @RequestParam("day") int day,
                                                                         @RequestParam("month") int month,
                                                                         @RequestParam("year") int year) {
        List<AppointmentDTO> list = service.findAllAppointmentsByDateAndBarber(day, month, year, barberId);
        return ResponseEntity.ok().body(list);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Void> updateAppointment(@PathVariable("id") long id, @RequestBody UpdateAppointmentRequest request) {
        service.updateAppointment(id, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/date")
    ResponseEntity<List<AppointmentDTO>> findAppointmentsByDate(@RequestParam("day") int day,
                                                                @RequestParam("month") int month,
                                                                @RequestParam("year") int year) {
        List<AppointmentDTO> list = service.findAppointmentsByDate(day, month, year);
        return ResponseEntity.ok().body(list);
    }
}
