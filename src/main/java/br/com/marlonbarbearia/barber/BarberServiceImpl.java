package br.com.marlonbarbearia.barber;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BarberServiceImpl {

    private final BarberRepository repository;
}
