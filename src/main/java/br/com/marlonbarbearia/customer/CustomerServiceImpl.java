package br.com.marlonbarbearia.customer;

import br.com.marlonbarbearia.enums.UserType;
import br.com.marlonbarbearia.exceptions.ObjectAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public Customer findCustomerEntityById(Long customerId) {
        return this.repository.findById(customerId)
                .orElseThrow(() -> new ObjectNotFoundException(customerId, Customer.class.getSimpleName()));
    }

    public CustomerResponse findCustomerById(Long customerId) {
        Optional<Customer> customerOptional = this.repository.findCustomerById(customerId);
        if(customerOptional.isEmpty()) {
            throw new ObjectNotFoundException(customerId, Customer.class.getSimpleName());
        }
        return CustomerMapper.customerEntityToResponse(customerOptional.get());
    }

    @Override
    public List<CustomerResponse> findAllCustomers() {
        return this.repository.findAllCustomers()
                .stream()
                .map(customer -> CustomerMapper.customerEntityToResponse(customer))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Long customerId) {
        this.repository.deleteById(customerId);
    }

    @Override
    public void createCustomer(CustomerRequest request) {
        if(!this.repository.findCustomerByPhoneNumber(request.phoneNumber()).isEmpty()){
            throw new ObjectAlreadyExistsException
                    ("Customer with phone number: [" + request.phoneNumber() + "] already exists");
        }
//        this.repository.save(Customer.builder()
//                .name(request.name())
//                .phoneNumber(request.phoneNumber())
//                .roles(Set.of(UserType.CUSTOMER))
//                .build()
//        );
    }

    @Override
    public CustomerResponse findCustomerByPhoneNumber(String phoneNumber) {
        Optional<Customer> customerOptional = this.repository.findCustomerByPhoneNumber(phoneNumber);
        if(customerOptional.isEmpty()) {
            throw new ObjectNotFoundException(phoneNumber, Customer.class.getSimpleName());
        }
        return CustomerMapper.customerEntityToResponse(customerOptional.get());
    }
}


//TODO: selecao de servicos -> adicionar o tempo de acordo com selecao
// Corte de cabelo - 40m - 25$
// barba - 20m - 20$
// sobrancelha - 5m - 5$
// pigmentacao - 10m - 10$
// enviar msg por wpp
// dia de funcionamento -> 2a 3a tattoo, resto barbearia
