package br.com.marlonbarbearia.appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
	
	List<AppointmentDTO> findAllAppointments();
	AppointmentDTO findAppointmentById(Long appointmentId);
	void deleteAppointmentById(Long appointmentId);
	AppointmentDTO updateAppointment(Long appointmentId, CreateAppointmentRequest appointment);
	Boolean appointmentHasTimeConflict(LocalDateTime appointmentDate, Long barberId);
	void createAppointment(CreateAppointmentRequest request);
	List<AppointmentDTO> findAppointmentsByDate(Integer day, Integer month, Integer year);
	List<AppointmentDTO> findAllAppointmentsByBarber(Long barberId);
	List<AppointmentDTO> findAllAppointmentsByDateAndBarber(Integer day, Integer month, Integer year, Long barberId);
}
