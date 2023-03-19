package br.com.marlonbarbearia.barber;

import br.com.marlonbarbearia.user.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/barbers")
@RequiredArgsConstructor
public class BarberController {

    private final BarberService service;

    @GetMapping("/{barberId}")
    public ResponseEntity<BarberDTO> findBarberById(@PathVariable("barberId") Long barberId) {
        BarberDTO barber = service.findBarberById(barberId);
        return ResponseEntity.ok().body(barber);
    }

    @GetMapping
    public ResponseEntity<List<BarberDTO>> findAllBarbers() {
        List<BarberDTO> list = service.findAllBarbers();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Void> createNewBarber(@RequestBody CreateUserRequest barberRequest) {
        service.createNewBarber(barberRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
