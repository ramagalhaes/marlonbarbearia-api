package br.com.marlonbarbearia.barbershop.barber;

import br.com.marlonbarbearia.account.AccountService;
import br.com.marlonbarbearia.account.AccountType;
import br.com.marlonbarbearia.account.CreateAccountRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class BarberService {

    private final BarberRepository repository;
    private final AccountService accountService;
    private final BCryptPasswordEncoder passwordEncoder;

    public Barber findBarberEntityById(Long barberId) {
        Optional<Barber> barberOptional = repository.findById(barberId);
        return barberOptional
                .orElseThrow(() -> new ObjectNotFoundException(barberId, Barber.class.getSimpleName()));
    }

    public BarberDTO findBarberById(Long barberId) {
        Optional<BarberDTO> barberOptional = repository.findBarberById(barberId);
        return barberOptional.orElseThrow(
                () -> new ObjectNotFoundException(barberId, Barber.class.getSimpleName()
        ));
    }

    public List<BarberDTO> findAllBarbers() {
        return repository.findAllBarbers();
    }

    public void deleteBarberById(Long barberId) {
        findBarberById(barberId);
        repository.deleteById(barberId);
    }

    public void createNewBarber(CreateAccountRequest barberRequest) {
        accountService.isPhoneNumberTaken(barberRequest.phoneNumber());
        repository.save(
                Barber.builder()
                        .name(barberRequest.name())
                        .lastName(barberRequest.lastName())
                        .phoneNumber(barberRequest.phoneNumber())
                        .password(passwordEncoder.encode(barberRequest.password()))
                        .roles(Set.of(AccountType.BARBER))
                        .build()
        );
        log.info("Creating barber from {}", barberRequest);
    }
}
