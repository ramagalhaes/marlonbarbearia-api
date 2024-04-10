package br.com.marlonbarbearia.barbershop.barber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BarberRepository extends JpaRepository<Barber, Long> {

    @Query("SELECT new br.com.marlonbarbearia.barbershop.barber.BarberDTO(b.id, b.name, b.lastName, b.phoneNumber) " +
            "FROM Barber b")
    List<BarberDTO> findAllBarbers();

    @Query("SELECT new br.com.marlonbarbearia.barbershop.barber.BarberDTO(b.id, b.name, b.lastName, b.phoneNumber) " +
            "FROM Barber b " +
            "WHERE b.id = :barberId")
    Optional<BarberDTO> findBarberById(@Param("barberId") Long barberId);

    @Query("SELECT new br.com.marlonbarbearia.barbershop.barber.BarberDTO(b.id, b.name, b.lastName, b.phoneNumber) " +
            "FROM Barber b " +
            "WHERE b.phoneNumber = :phoneNumber")
    Optional<BarberDTO> findBarberByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT b FROM Barber b " +
            "WHERE b.phoneNumber = :phoneNumber")
    Optional<Barber> findBarberEntityByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
