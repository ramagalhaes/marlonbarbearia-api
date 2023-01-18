package br.com.marlonbarbearia.barber;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/barbers")
@RequiredArgsConstructor
public class BarberController {

    private final BarberService service;

    @GetMapping("/{barberId}")
    public ResponseEntity<BarberResponse> findBarberById(@PathVariable("barberId") Long barberId) {
        BarberResponse barber = service.findBarberById(barberId);
        return ResponseEntity.ok().body(barber);
    }

    @GetMapping
    public ResponseEntity<List<BarberResponse>> findAllBarbers() {
        List<BarberResponse> list = service.findAllBarbers();
        return ResponseEntity.ok().body(list);
    }

}
