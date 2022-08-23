package br.com.marlonbarbearia.barber;

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
        return null;
    }

    @Override
    public List<BarberResponse> findAllBarbers() {
        return null;
    }

    @Override
    public Barber findBarberEntityByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public void deleteBarberById(Long BarberId) {

    }

    @Override
    public void createNewBarber(BarberRequest barberRequest) {

    }
}
