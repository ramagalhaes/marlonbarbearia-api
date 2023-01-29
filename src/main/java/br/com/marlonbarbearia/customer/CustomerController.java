package br.com.marlonbarbearia.customer;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
