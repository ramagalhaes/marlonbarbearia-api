package br.com.marlonbarbearia.barber;

import java.util.List;

public interface BarberService {
    Barber findBarberEntityById(Long barberId);
    BarberDTO findBarberById(Long barberId);
    List<BarberDTO> findAllBarbers();
    Barber findBarberEntityByPhoneNumber(String phoneNumber);
    void deleteBarberById(Long barberId);
    void createNewBarber(BarberRequest barberRequest);
}
