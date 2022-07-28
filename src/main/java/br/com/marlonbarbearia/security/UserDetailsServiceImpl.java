package br.com.marlonbarbearia.security;

import br.com.marlonbarbearia.customer.Customer;
import br.com.marlonbarbearia.customer.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = this.customerRepository.findCustomerByPhoneNumber(phoneNumber);
        if(customerOptional.isEmpty()) { throw new UsernameNotFoundException(phoneNumber); }
        Customer customer = customerOptional.get();
        return UserSpringSecurity.builder()
                .id(customer.getId())
                .password(customer.getPassword())
                .phoneNumber(customer.getPhoneNumber())
                .authorities(
                        customer.getRoles()
                                .stream()
                                .map(role ->  new SimpleGrantedAuthority(role.getDescription()))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
