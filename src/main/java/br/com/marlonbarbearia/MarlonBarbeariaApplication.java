package br.com.marlonbarbearia;

import br.com.marlonbarbearia.barber.Barber;
import br.com.marlonbarbearia.barber.BarberRepository;
import br.com.marlonbarbearia.customer.Customer;
import br.com.marlonbarbearia.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarlonBarbeariaApplication implements CommandLineRunner {

    @Autowired
    private BarberRepository barberRepository;
    @Autowired
    private CustomerRepository customerRepository;

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
                .build());
    }
}
