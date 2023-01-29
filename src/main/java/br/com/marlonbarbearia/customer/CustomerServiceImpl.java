package br.com.marlonbarbearia.customer;

import br.com.marlonbarbearia.exceptions.ObjectAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public Customer findCustomerEntityById(Long customerId) {
        log.info("CustomerServiceImpl::findCustomerEntityById() Finding customer with id {}" , customerId);
        return repository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(customerId, Customer.class.getSimpleName()));
    }

    @Override
    public CustomerDTO findCustomerById(Long customerId) {
        log.info("CustomerServiceImpl::findCustomerById() Finding customer with id {}" , customerId);
        Optional<Customer> customerOptional = repository.findCustomerById(customerId);
        if(customerOptional.isEmpty()) {
            throw new ObjectNotFoundException(customerId, Customer.class.getSimpleName());
        }
        return CustomerMapper.customerEntityToResponse(customerOptional.get());
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        log.info("CustomerServiceImpl::findAllCustomers() Finding all customers");
        return repository.findAllCustomers()
                .stream()
                .map(CustomerMapper::customerEntityToResponse)
                .collect(toList());
    }

    @Override
    public void deleteCustomer(Long customerId) {
        log.info("CustomerServiceImpl::deleteCustomerById() Deleting customer with id {}" , customerId);
        repository.deleteById(customerId);
    }

    @Override
    public void createCustomer(CustomerRequest request) {
        log.info("CustomerServiceImpl::createCustomer() Creating customer from {}", request);
        boolean phoneNumberIsTaken = repository.findCustomerByPhoneNumber(request.phoneNumber()).isPresent();
        if(phoneNumberIsTaken){
            throw new ObjectAlreadyExistsException
                    ("Customer with phone number: [" + request.phoneNumber() + "] already exists");
        }
        repository.save(Customer.builder()
                .name(request.name())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .build()
        );
        log.info("CustomerServiceImpl::createCustomer() Customer created {}", request);
    }

    @Override
    public CustomerDTO findCustomerByPhoneNumber(String phoneNumber) {
        log.info("CustomerServiceImpl::findCustomerByPhoneNumber() Finding customer with phone number {}", phoneNumber);
        Optional<Customer> customerOptional = repository.findCustomerByPhoneNumber(phoneNumber);
        if(customerOptional.isEmpty()) {
            throw new ObjectNotFoundException(phoneNumber, Customer.class.getSimpleName());
        }
        return CustomerMapper.customerEntityToResponse(customerOptional.get());
    }
}


//TODO: enviar msg por wpp

