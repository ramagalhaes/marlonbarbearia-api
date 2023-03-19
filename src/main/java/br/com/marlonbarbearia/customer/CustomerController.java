package br.com.marlonbarbearia.customer;

import br.com.marlonbarbearia.enums.UserType;
import br.com.marlonbarbearia.user.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable("customerId") Long customerId) {
        CustomerDTO response = service.findCustomerById(customerId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<CustomerDTO> findCustomerByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        CustomerDTO response = service.findCustomerByPhoneNumber(phoneNumber);
        return ResponseEntity.ok().body(response);
    }

@PostMapping
    public ResponseEntity<Void> createNewUser(@RequestBody CreateUserRequest userRequest) {
        service.createCustomer(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
