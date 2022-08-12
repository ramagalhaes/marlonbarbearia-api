package br.com.marlonbarbearia.customer;

import java.util.List;

public interface CustomerService {

    Customer findCustomerEntityById(Long customerId);
    CustomerResponse findCustomerById(Long customerId);
    List<CustomerResponse> findAllCustomers();
    void deleteCustomer(Long customerId);
    void createCustomer(CustomerRequest request);
    CustomerResponse findCustomerByPhoneNumber(String phoneNumber);
}
