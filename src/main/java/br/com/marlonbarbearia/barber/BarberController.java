package br.com.marlonbarbearia.barber;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/barbers")
@AllArgsConstructor
public class BarberController {

    private final BarberService service;

    @GetMapping("/{barberId}")
    public ResponseEntity<Barber> findBarberById(@PathVariable("barberId") Long barberId) {
        Barber barber = this.service.findBarberEntityById(barberId);
        return ResponseEntity.ok().body(barber);
    }
}
