package br.com.marlonbarbearia.barber;

import br.com.marlonbarbearia.enums.UserType;
import br.com.marlonbarbearia.user.CreateUserRequest;
import br.com.marlonbarbearia.user.UserService;
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
public class BarberServiceImpl implements BarberService {

    private final BarberRepository repository;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public Barber findBarberEntityById(Long barberId) {
        Optional<Barber> barberOptional = repository.findById(barberId);
        return barberOptional
                .orElseThrow(() -> new ObjectNotFoundException(barberId, Barber.class.getSimpleName()));
    }

    @Override
    public BarberDTO findBarberById(Long barberId) {
        Optional<BarberDTO> barberOptional = repository.findBarberById(barberId);
        return barberOptional.orElseThrow(
                () -> new ObjectNotFoundException(barberId, Barber.class.getSimpleName()
        ));
    }

    @Override
    public List<BarberDTO> findAllBarbers() {
        return repository.findAllBarbers();
    }

    @Override
    public Barber findBarberEntityByPhoneNumber(String phoneNumber) {
        Optional<Barber> barberOptional = repository.findBarberEntityByPhoneNumber(phoneNumber);
        return barberOptional.orElseThrow(
                () -> new ObjectNotFoundException(phoneNumber, Barber.class.getSimpleName()
        ));
    }

    @Override
    public void deleteBarberById(Long barberId) {
        findBarberById(barberId);
        repository.deleteById(barberId);
    }

    @Override
    public void createNewBarber(CreateUserRequest barberRequest) {
        userService.isPhoneNumberTaken(barberRequest.phoneNumber());
        repository.save(
                Barber.builder()
                        .name(barberRequest.name())
                        .lastName(barberRequest.lastName())
                        .phoneNumber(barberRequest.phoneNumber())
                        .password(passwordEncoder.encode(barberRequest.password()))
                        .roles(Set.of(UserType.BARBER))
                        .build()
        );
        log.info("Creating barber from {}", barberRequest);
    }
}
