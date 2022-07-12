package br.com.marlonbarbearia.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a " +
            "FROM Appointment  a")
    List<Appointment> findAllAppointments();

    @Query("SELECT a " +
            "FROM Appointment a " +
            "WHERE DAY(a.date) = :day " +
            "AND MONTH(a.date) = :month " +
            "AND YEAR(a.date) = :year")
    List<Appointment> findAppointmentsByDate(
            @Param("day") Integer day, @Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT a " +
            "FROM Appointment a " +
            "WHERE a.barber.id = :barberId")
    List<Appointment> findAllAppointmentsByBarberId(@Param("barberId") Long barberId);

    @Query("SELECT a " +
            "FROM Appointment a " +
            "WHERE a.barber.id = :barberId " +
            "AND DAY(a.date) = :day " +
            "AND MONTH(a.date) = :month " +
            "AND YEAR(a.date) = :year ")
    List<Appointment> findAppointmentsByDateAndBarberId(
            @Param("day") Integer day, @Param("month") Integer month,
            @Param("year") Integer year, @Param("barberId") Long barberId);

    @Query("SELECT a " +
            "FROM Appointment a " +
            "WHERE a.id = :appointmentId")
    Optional<Appointment> findAppointmentById(@Param("appointmentId") Long appointmentId);

}
