package br.com.marlonbarbearia.user;

import br.com.marlonbarbearia.barber.BarberRequest;
import br.com.marlonbarbearia.barber.BarberService;
import br.com.marlonbarbearia.customer.CustomerRequest;
import br.com.marlonbarbearia.customer.CustomerService;
import br.com.marlonbarbearia.enums.UserType;
import br.com.marlonbarbearia.exceptions.ObjectAlreadyExistsException;
import br.com.marlonbarbearia.security.UserSpringSecurity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService{

    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final BarberService barberService;
    private final BCryptPasswordEncoder passwordEncoder;

    public void isPhoneNumberTaken(String phoneNumber) {
        Optional<User> userOptional = userRepository.findUserByPhoneNumber(phoneNumber);
        if(userOptional.isPresent()) {
            throw new ObjectAlreadyExistsException("Phone Number: ["+ phoneNumber +"] is taken!");
        }
    }

    public void createNewCustomer(CreateUserRequest userRequest) {
        createNewUser(userRequest, UserType.CUSTOMER);
        customerService.createCustomer(
               CustomerRequest.builder()
                       .name(userRequest.name())
                       .lastName(userRequest.lastName())
                       .phoneNumber(userRequest.phoneNumber())
                       .build()
        );
    }

    public void createNewBarber(CreateUserRequest userRequest) {
        createNewUser(userRequest, UserType.BARBER);
        barberService.createNewBarber(
                BarberRequest.builder()
                        .name(userRequest.name())
                        .lastName(userRequest.lastName())
                        .phoneNumber(userRequest.phoneNumber())
                        .build()
        );
    }

    private void createNewUser(CreateUserRequest userRequest, UserType userType) {
        isPhoneNumberTaken(userRequest.phoneNumber());
        userRepository.save(
                User.builder()
                        .phoneNumber(userRequest.phoneNumber())
                        .password(passwordEncoder.encode(userRequest.password()))
                        .roles(Set.of(userType))
                        .build()
        );
        log.info("Creating user {}", userRequest.phoneNumber());
    }

    public static UserSpringSecurity getAuthenticatedUser() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }

}
