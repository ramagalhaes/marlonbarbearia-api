package br.com.marlonbarbearia;

import br.com.marlonbarbearia.barbershop.barber.BarberService;
import br.com.marlonbarbearia.customer.CustomerService;
import br.com.marlonbarbearia.account.CreateAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MarlonBarbeariaApplication implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BarberService barberService;

    public static void main(String[] args) { SpringApplication.run(MarlonBarbeariaApplication.class, args); }

    @Override
    public void run(String... args) throws Exception {

        customerService.createCustomer(
                CreateAccountRequest.builder()
                        .name("Raphael")
                        .lastName("Magalhães")
                        .phoneNumber("33994135")
                        .password(pe.encode("1234"))
                        .build()
        );
        barberService.createNewBarber(
                CreateAccountRequest.builder()
                        .name("Marlon")
                        .lastName("Vitor")
                        .phoneNumber("33994137")
                        .password(pe.encode("1234"))
                        .build()
        );
    }
}
