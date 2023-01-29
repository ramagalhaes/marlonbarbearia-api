package br.com.marlonbarbearia.customer;

public class CustomerMapper {

    private CustomerMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CustomerResponse customerEntityToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}
