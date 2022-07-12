package br.com.marlonbarbearia.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT new br.com.marlonbarbearia.customer.CustomerResponse(c.id, c.name, c.phoneNumber) " +
            "FROM Customer c " +
            "WHERE c.id = :customerId")
    Optional<CustomerResponse> findCustomerById(@Param("customerId") Long customerId);

    @Query("SELECT new br.com.marlonbarbearia.customer.CustomerResponse(c.id, c.name, c.phoneNumber) " +
            "FROM Customer c")
    List<CustomerResponse> findAllCustomers();

    @Query("SELECT new br.com.marlonbarbearia.customer.CustomerResponse(c.id, c.name, c.phoneNumber) " +
            "FROM Customer c " +
            "WHERE c.phoneNumber = :phoneNumber")
    Optional<CustomerResponse> findCustomerByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
