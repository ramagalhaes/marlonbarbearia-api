package br.com.marlonbarbearia.barber;

import java.util.List;

public interface BarberService {
    Barber findBarberEntityById(Long barberId);
    BarberResponse findBarberById(Long barberId);
    List<BarberResponse> findAllBarbers();
    Barber findBarberEntityByPhoneNumber(String phoneNumber);
    void deleteBarberById(Long BarberId);
    void createNewBarber(BarberRequest barberRequest);
}
