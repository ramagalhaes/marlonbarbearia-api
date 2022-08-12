package br.com.marlonbarbearia.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c " +
            "FROM Customer c " +
            "WHERE c.id = :customerId")
    Optional<Customer> findCustomerById(@Param("customerId") Long customerId);

    @Query("SELECT c " +
            "FROM Customer c")
    List<Customer> findAllCustomers();

    @Query("SELECT c " +
            "FROM Customer c " +
            "WHERE c.phoneNumber = :phoneNumber")
    Optional<Customer> findCustomerByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
