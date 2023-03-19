package br.com.marlonbarbearia.customer;

import br.com.marlonbarbearia.user.CreateUserRequest;
import br.com.marlonbarbearia.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public Customer findCustomerEntityById(Long customerId) {
        log.info("CustomerServiceImpl::findCustomerEntityById() start");
        return repository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(customerId, Customer.class.getSimpleName()));
    }

    @Override
    public CustomerDTO findCustomerById(Long customerId) {
        log.info("CustomerServiceImpl::findCustomerById() start");
        Optional<Customer> customerOptional = repository.findCustomerById(customerId);
        if(customerOptional.isEmpty()) {
            throw new ObjectNotFoundException(customerId, Customer.class.getSimpleName());
        }
        return CustomerMapper.entityToResponse(customerOptional.get());
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        log.info("CustomerServiceImpl::findAllCustomers() Finding all customers");
        return repository.findAllCustomers()
                .stream()
                .map(CustomerMapper::entityToResponse)
                .collect(toList());
    }

    @Override
    public void deleteCustomer(Long customerId) {
        log.info("CustomerServiceImpl::deleteCustomerById() Deleting customer with id {}" , customerId);
        repository.deleteById(customerId);
    }

    @Override
    public void createCustomer(CreateUserRequest request) {
        log.info("CustomerServiceImpl::createCustomer() start");
        userService.isPhoneNumberTaken(request.phoneNumber());
        repository.save(
                Customer.builder()
                .name(request.name())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .password(passwordEncoder.encode(request.password()))
                .build()
        );
        log.info("CustomerServiceImpl::createCustomer() Customer created {}", request);
    }

    @Override
    public CustomerDTO findCustomerByPhoneNumber(String phoneNumber) {
        log.info("CustomerServiceImpl::findCustomerByPhoneNumber() start");
        Optional<Customer> customerOptional = repository.findCustomerByPhoneNumber(phoneNumber);
        if(customerOptional.isEmpty()) {
            throw new ObjectNotFoundException(phoneNumber, Customer.class.getSimpleName());
        }
        log.info("CustomerServiceImpl::findCustomerByPhoneNumber() customer found");
        return CustomerMapper.entityToResponse(customerOptional.get());
    }
}


//TODO: enviar msg por wpp

