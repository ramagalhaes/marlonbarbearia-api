package br.com.marlonbarbearia.customer;

public class CustomerMapper {

    private CustomerMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CustomerDTO customerEntityToResponse(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}
