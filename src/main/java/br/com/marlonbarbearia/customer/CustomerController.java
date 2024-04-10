package br.com.marlonbarbearia.customer;

import br.com.marlonbarbearia.account.CreateAccountRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customers")
public record CustomerController(CustomerService service) {

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable("id") Long id) {
        Customer response = service.findCustomerEntityById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Void> createNewCustomer(@RequestBody CreateAccountRequest userRequest) {
        service.createCustomer(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
