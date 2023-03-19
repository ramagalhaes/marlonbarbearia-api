package br.com.marlonbarbearia.customer;

import br.com.marlonbarbearia.user.CreateUserRequest;

import java.util.List;

public interface CustomerService {

    Customer findCustomerEntityById(Long customerId);
    CustomerDTO findCustomerById(Long customerId);
    List<CustomerDTO> findAllCustomers();
    void deleteCustomer(Long customerId);
    void createCustomer(CreateUserRequest request);
    CustomerDTO findCustomerByPhoneNumber(String phoneNumber);
}
