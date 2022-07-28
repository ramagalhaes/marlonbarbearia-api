package br.com.marlonbarbearia;

import br.com.marlonbarbearia.barber.Barber;
import br.com.marlonbarbearia.barber.BarberRepository;
import br.com.marlonbarbearia.customer.Customer;
import br.com.marlonbarbearia.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MarlonBarbeariaApplication implements CommandLineRunner {

    @Autowired
    private BarberRepository barberRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(MarlonBarbeariaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.barberRepository.save(Barber.builder()
                .name("Marlon")
                .phoneNumber("33994135")
                .build());

        this.customerRepository.save(Customer.builder()
                .name("Raphael")
                .phoneNumber("33994135")
                .password(passwordEncoder.encode("12341234"))
                .build());
    }
}
