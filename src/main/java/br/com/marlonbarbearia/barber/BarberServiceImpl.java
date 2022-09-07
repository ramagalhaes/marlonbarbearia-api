package br.com.marlonbarbearia.barber;

import br.com.marlonbarbearia.exceptions.ObjectAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class BarberServiceImpl implements BarberService {

    private final BarberRepository repository;

    public Barber findBarberEntityById(Long barberId) {
        Optional<Barber> barberOptional = this.repository.findById(barberId);
        return barberOptional
                .orElseThrow(() -> new ObjectNotFoundException(barberId, Barber.class.getSimpleName()));
    }

    @Override
    public BarberResponse findBarberById(Long barberId) {
        Optional<BarberResponse> barberOptional = repository.findBarberById(barberId);
        return barberOptional.orElseThrow(
                () -> new ObjectNotFoundException(barberId, Barber.class.getSimpleName()
        ));
    }

    @Override
    public List<BarberResponse> findAllBarbers() {
        List<BarberResponse> barbers = repository.findAllBarbers();
        return barbers;
    }

    @Override
    public Barber findBarberEntityByPhoneNumber(String phoneNumber) {
        Optional<Barber> barberOptional = this.repository.findBarberEntityByPhoneNumber(phoneNumber);
        return barberOptional.orElseThrow(
                () -> new ObjectNotFoundException(phoneNumber, Barber.class.getSimpleName()
                ));
    }

    @Override
    public void deleteBarberById(Long barberId) {
        this.findBarberById(barberId);
        this.repository.deleteById(barberId);
    }

    @Override
    public void createNewBarber(BarberRequest barberRequest) {
        if(this.repository.findBarberEntityByPhoneNumber(barberRequest.phoneNumber()).isPresent()) {
            throw new ObjectAlreadyExistsException("Phone number [" + barberRequest.phoneNumber() + "] is already taken");
        }
        this.repository.save(
                Barber.builder()
                        .name(barberRequest.name())
                        .lastName(barberRequest.lastName())
                        .phoneNumber(barberRequest.phoneNumber())
                        .build()
        );
    }
}
