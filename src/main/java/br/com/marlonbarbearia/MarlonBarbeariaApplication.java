package br.com.marlonbarbearia;

import br.com.marlonbarbearia.enums.UserType;
import br.com.marlonbarbearia.user.User;
import br.com.marlonbarbearia.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class MarlonBarbeariaApplication implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private UserRepository userRepository;
    public static void main(String[] args) { SpringApplication.run(MarlonBarbeariaApplication.class, args); }

    @Override
    public void run(String... args) throws Exception {
        this.userRepository.save(
                User.builder()
                        .name("Raphael")
                        .lastName("Magalhaes")
                        .phoneNumber("33994135")
                        .roles(Set.of(UserType.CUSTOMER))
                        .password(pe.encode("1234"))
                        .build()
        );
    }
}
