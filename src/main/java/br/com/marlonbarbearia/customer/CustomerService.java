package br.com.marlonbarbearia.customer;

import java.util.List;

public interface CustomerService {

    Customer findCustomerEntityById(Long customerId);
    CustomerDTO findCustomerById(Long customerId);
    List<CustomerDTO> findAllCustomers();
    void deleteCustomer(Long customerId);
    void createCustomer(CustomerRequest request);
    CustomerDTO findCustomerByPhoneNumber(String phoneNumber);
}
