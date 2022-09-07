package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.barber.BarberService;
import br.com.marlonbarbearia.customer.CustomerService;
import br.com.marlonbarbearia.hairjob.HairJobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository repository;

    @Mock
    private CustomerService customerService;

    private BarberService barberService;

    private HairJobService hairJobService;

    private AppointmentService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AppointmentServiceImpl(repository, barberService, customerService, hairJobService);
    }

    @Test
    void itShouldFindAllAppointments() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void itShouldFindAppointmentById() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void deleteAppointmentById() {
    }

    @Test
    void updateAppointment() {
    }

    @Test
    void isAppointmentWithoutRangeOfExistingAppointments() {
    }

    @Test
    void createAppointment() {
    }

    @Test
    void findAppointmentsByDate() {
    }

    @Test
    void findAllAppointmentsByBarber() {
    }

    @Test
    void findAllAppointmentsByDateAndBarber() {
    }
}