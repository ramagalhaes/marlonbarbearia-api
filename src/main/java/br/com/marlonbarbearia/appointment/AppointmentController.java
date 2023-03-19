package br.com.marlonbarbearia.appointment;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/appointments")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    ResponseEntity<Void> createAppointment(@RequestBody CreateAppointmentRequest createAppointmentRequest) {
        service.createAppointment(createAppointmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{appointmentId}")
    ResponseEntity<Void> deleteAppointment(@PathVariable("appointmentId") Long appointmentId) {
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
    ResponseEntity<AppointmentDTO> findAppointmentById(@PathVariable("appointmentId") Long appointmentId) {
        AppointmentDTO appointment = service.findAppointmentById(appointmentId);
        return ResponseEntity.ok().body(appointment);
    }

    @GetMapping("/barber")
    ResponseEntity<List<AppointmentDTO>> findAllAppointmentsByBarber(@RequestParam("barber-id") Long barberId) {
        List<AppointmentDTO> response = service.findAllAppointmentsByBarber(barberId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/barber/{barberId}/date")
    ResponseEntity<List<AppointmentDTO>> findAppointmentsByDateAndBarber(@PathVariable("barberId") Long barberId,
                                                                         @RequestParam("day") Integer day,
                                                                         @RequestParam("month") Integer month,
                                                                         @RequestParam("year") Integer year) {
        List<AppointmentDTO> list = service.findAllAppointmentsByDateAndBarber(day, month, year, barberId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/date")
    ResponseEntity<List<AppointmentDTO>> findAppointmentsByDate(@RequestParam("day") Integer day,
                                                                @RequestParam("month") Integer month,
                                                                @RequestParam("year") Integer year) {
        List<AppointmentDTO> list = service.findAppointmentsByDate(day, month, year);
        return ResponseEntity.ok().body(list);
    }
}
