package br.com.marlonbarbearia.user;

import br.com.marlonbarbearia.barber.BarberService;
import br.com.marlonbarbearia.customer.CustomerRequest;
import br.com.marlonbarbearia.customer.CustomerService;
import br.com.marlonbarbearia.enums.UserType;
import br.com.marlonbarbearia.exceptions.ObjectAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final BarberService barberService;
    private final BCryptPasswordEncoder passwordEncoder;

    public Boolean isPhoneNumberTaken(String phoneNumber) {
        Optional<User> userOptional = this.userRepository.findUserByPhoneNumber(phoneNumber);
        if(userOptional.isPresent()) { return true; }
        return false;
    }

    public void createNewCustomer(CreateUserRequest userRequest) {
       if(isPhoneNumberTaken(userRequest.phoneNumber())) {
           throw new ObjectAlreadyExistsException("User with: [] already exists!");
       }
       String name = userRequest.name();
       String lastName = userRequest.lastName();
       String phoneNumber = userRequest.phoneNumber();
        this.userRepository.save(
                User.builder()
                        .name(name)
                        .lastName(lastName)
                        .phoneNumber(phoneNumber)
                        .password(passwordEncoder.encode(userRequest.password()))
                        .roles(Set.of(UserType.CUSTOMER))
                        .build()
        );
       this.customerService.createCustomer(
               CustomerRequest.builder()
                       .name(name)
                       .lastName(lastName)
                       .phoneNumber(phoneNumber)
                       .build()
       );
    }

//    public void createNewBarber(CreateUserRequest userRequest) {
//        if(findUserByPhoneNumber(userRequest.phoneNumber()) != null) {
//            throw new ObjectAlreadyExistsException("User with: [] already exists!");
//        }
//        String name = userRequest.name();
//        String lastName = userRequest.lastName();
//        String phoneNumber = userRequest.phoneNumber();
//        this.userRepository.save(
//                User.builder()
//                        .name(name)
//                        .lastName(lastName)
//                        .phoneNumber(phoneNumber)
//                        .password(passwordEncoder.encode(userRequest.password()))
//                        .roles(Set.of(UserType.CUSTOMER))
//                        .build()
//        );
//        this.barberService.(
//                CustomerRequest.builder()
//                        .name(name)
//                        .lastName(lastName)
//                        .phoneNumber(phoneNumber)
//                        .build()
//        );
//    }

}
