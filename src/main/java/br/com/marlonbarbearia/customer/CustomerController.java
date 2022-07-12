package br.com.marlonbarbearia.customer;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable("customerId") Long customerId) {
        CustomerResponse response = this.service.findCustomerById(customerId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<CustomerResponse> findCustomerByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        CustomerResponse response = this.service.findCustomerByPhoneNumber(phoneNumber);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Void> createNewCustomer(@RequestBody CustomerRequest request) {
        this.service.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
