package br.com.marlonbarbearia.barber;

import br.com.marlonbarbearia.barber.Barber;

public interface BarberService {
    Barber findBarberEntityById(Long barberId);
}
