package br.com.marlonbarbearia.customer;

import br.com.marlonbarbearia.customer.Customer;
import br.com.marlonbarbearia.customer.CustomerRequest;
import br.com.marlonbarbearia.customer.CustomerResponse;

import java.util.List;

public interface CustomerService {

    Customer findCustomerEntityById(Long customerId);
    CustomerResponse findCustomerById(Long customerId);
    List<CustomerResponse> findAllCustomers();
    void deleteCustomer(Long customerId);
    void createCustomer(CustomerRequest request);
    CustomerResponse findCustomerByPhoneNumber(String phoneNumber);
}
