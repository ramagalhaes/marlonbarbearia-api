package br.com.marlonbarbearia.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/customer")
    public ResponseEntity<Void> createNewUser(@RequestBody CreateUserRequest userRequest) {
        userService.createNewCustomer(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/barber")
    public ResponseEntity<Void> createNewBarber(@RequestBody CreateUserRequest userRequest) {
        userService.createNewBarber(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
