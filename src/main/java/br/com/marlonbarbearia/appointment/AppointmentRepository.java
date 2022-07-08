package br.com.marlonbarbearia.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT new br.com.marlonbarbearia.appointment.AppointmentResponse" +
            "(a.id, a.barberId, a.createdAt, a.customerId, a.date) " +
            "FROM Appointment  a")
    List<AppointmentResponse> findAllAppointments();

    @Query("SELECT new br.com.marlonbarbearia.appointment.AppointmentResponse" +
            "(a.id, a.barberId, a.createdAt, a.customerId, a.date) " +
            "FROM Appointment a " +
            "WHERE DAY(a.date) = :day " +
            "AND MONTH(a.date) = :month " +
            "AND YEAR(a.date) = :year")
    List<AppointmentResponse> findAppointmentsByDate(
            @Param("day") Integer day, @Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT new br.com.marlonbarbearia.appointment.AppointmentResponse" +
            "(a.id, a.barberId, a.createdAt, a.customerId, a.date) " +
            "FROM Appointment a " +
            "WHERE a.barberId = :barberId")
    List<AppointmentResponse> findAllAppointmentsByBarberId(@Param("barberId") Long barberId);

    @Query("SELECT new br.com.marlonbarbearia.appointment.AppointmentResponse" +
            "(a.id, a.barberId, a.createdAt, a.customerId, a.date) " +
            "FROM Appointment a " +
            "WHERE a.barberId = :barberId " +
            "AND DAY(a.date) = :day " +
            "AND MONTH(a.date) = :month " +
            "AND YEAR(a.date) = :year ")
    List<AppointmentResponse> findAppointmentsByDateAndBarberId(
            @Param("day") Integer day, @Param("month") Integer month,
            @Param("year") Integer year, @Param("barberId") Long barberId);

    @Query("SELECT new br.com.marlonbarbearia.appointment.AppointmentResponse" +
            "(a.id, a.barberId, a.createdAt, a.customerId, a.date) " +
            "FROM Appointment a " +
            "WHERE a.id = :appointmentId")
    Optional<AppointmentResponse> findAppointmentById(@Param("appointmentId") Long appointmentId);

}
