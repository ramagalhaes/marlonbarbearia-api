package br.com.marlonbarbearia.barbershop.barber;

import br.com.marlonbarbearia.account.CreateAccountRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/barbers")
public record BarberController(BarberService service) {


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
    public ResponseEntity<Void> createNewBarber(@RequestBody CreateAccountRequest barberRequest) {
        service.createNewBarber(barberRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarber(@PathVariable("id") Long id) {
        service.deleteBarberById(id);
        return ResponseEntity.noContent().build();
    }

}
