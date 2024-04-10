package br.com.marlonbarbearia.customer;

import br.com.marlonbarbearia.account.AccountService;
import br.com.marlonbarbearia.account.AccountStatus;
import br.com.marlonbarbearia.account.CreateAccountRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;
    private final Clock clock;

    public Customer findCustomerEntityById(Long customerId) {
        log.info("CustomerServiceImpl::findCustomerEntityById() - searching for customer with id: {}", customerId);
        return repository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(customerId, Customer.class.getSimpleName()));
    }

    public CustomerDTO findCustomerById(Long customerId) {
        log.info("CustomerServiceImpl::findCustomerById() - Find customer by id: {}", customerId);
        Optional<Customer> customerOptional = repository.findCustomerById(customerId);
        if(customerOptional.isEmpty()) {
            throw new ObjectNotFoundException(customerId, Customer.class.getSimpleName());
        }
        return CustomerMapper.entityToResponse(customerOptional.get());
    }

    public void deleteCustomer(Long customerId) {
        log.info("CustomerServiceImpl::deleteCustomerById() - deleting customer with id {}" , customerId);
        repository.deleteById(customerId);
    }

    public void createCustomer(CreateAccountRequest request) {
        log.info("CustomerServiceImpl::createCustomer() - Create customer request received: {}", request);
        accountService.isPhoneNumberTaken(request.phoneNumber());
        Customer customer = repository.save(
                Customer.builder()
                        .name(request.name())
                        .lastName(request.lastName())
                        .phoneNumber(request.phoneNumber())
                        .createdAt(LocalDateTime.now(clock))
                        .password(passwordEncoder.encode(request.password()))
                        .status(AccountStatus.CREATED.getCode())
                .build()
        );
        log.info("CustomerServiceImpl::createCustomer() - Customer created");
        accountService.createAccountConfirmation(customer);
    }

    public boolean isAccountConfirmed(Customer customer) {
        return customer.isAccountConfirmed();
    }
}
