package br.com.marlonbarbearia.customer;

import br.com.marlonbarbearia.exceptions.ObjectAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public Customer findCustomerEntityById(Long customerId) {
        return repository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(customerId, Customer.class.getSimpleName()));
    }

    @Override
    public CustomerResponse findCustomerById(Long customerId) {
        Optional<Customer> customerOptional = repository.findCustomerById(customerId);
        if(customerOptional.isEmpty()) {
            throw new ObjectNotFoundException(customerId, Customer.class.getSimpleName());
        }
        return CustomerMapper.customerEntityToResponse(customerOptional.get());
    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        return repository.findAllCustomers()
                .stream()
                .map(CustomerMapper::customerEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Long customerId) {
        repository.deleteById(customerId);
    }

    @Override
    public void createCustomer(CustomerRequest request) {
        if(repository.findCustomerByPhoneNumber(request.phoneNumber()).isPresent()){
            throw new ObjectAlreadyExistsException
                    ("Customer with phone number: [" + request.phoneNumber() + "] already exists");
        }
        repository.save(Customer.builder()
                .name(request.name())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .build()
        );
        log.info("Creating customer from {}", request);
    }

    @Override
    public CustomerResponse findCustomerByPhoneNumber(String phoneNumber) {
        Optional<Customer> customerOptional = repository.findCustomerByPhoneNumber(phoneNumber);
        if(customerOptional.isEmpty()) {
            throw new ObjectNotFoundException(phoneNumber, Customer.class.getSimpleName());
        }
        return CustomerMapper.customerEntityToResponse(customerOptional.get());
    }
}


//TODO: enviar msg por wpp

