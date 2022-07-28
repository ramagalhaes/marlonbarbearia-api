package br.com.marlonbarbearia.customer;

public class CustomerMapper {

    public static CustomerResponse customerEntityToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .roles(customer.getRoles())
                .build();
    }

}
