package br.com.marlonbarbearia.user;

import br.com.marlonbarbearia.exceptions.ObjectAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService{

    private final UserRepository userRepository;

    public void isPhoneNumberTaken(String phoneNumber) {
        Optional<User> userOptional = userRepository.findUserByPhoneNumber(phoneNumber);
        if(userOptional.isPresent()) {
            throw new ObjectAlreadyExistsException("Phone Number: ["+ phoneNumber +"] is already in use");
        }
    }

//    public static User getAuthenticatedUser() {
//        try {
//            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        } catch (Exception e) {
//            return null;
//        }
//    }

}
