package br.com.marlonbarbearia.barber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BarberRepository extends JpaRepository<Barber, Long> {

    @Query("SELECT new br.com.marlonbarbearia.barber.BarberResponse(b.id, b.name, b.lastName, b.phoneNumber) " +
            "FROM Barber b")
    List<BarberResponse> findAllBarbers();

    @Query("SELECT new br.com.marlonbarbearia.barber.BarberResponse(b.id, b.name, b.lastName, b.phoneNumber) " +
            "FROM Barber b " +
            "WHERE b.id = :barberId")
    Optional<BarberResponse> findBarberById(@Param("barberId") Long barberId);

    @Query("SELECT new br.com.marlonbarbearia.barber.BarberResponse(b.id, b.name, b.lastName, b.phoneNumber) " +
            "FROM Barber b " +
            "WHERE b.phoneNumber = :phoneNumber")
    Optional<BarberResponse> findBarberByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT b FROM Barber b " +
            "WHERE b.phoneNumber = :phoneNumber")
    Optional<Barber> findBarberEntityByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
