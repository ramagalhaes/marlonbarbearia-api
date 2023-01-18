package br.com.marlonbarbearia.appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
	
	List<AppointmentResponse> findAllAppointments();
	AppointmentResponse findAppointmentById(Long appointmentId);
	void deleteAppointmentById(Long appointmentId);
	AppointmentResponse updateAppointment(Long appointmentId, AppointmentRequest appointment);
	Boolean appointmentHasTimeConflict(LocalDateTime appointmentDate, Long barberId);
	void createAppointment(AppointmentRequest request);
	List<AppointmentResponse> findAppointmentsByDate(Integer day, Integer month, Integer year);
	List<AppointmentResponse> findAllAppointmentsByBarber(Long barberId);
	List<AppointmentResponse> findAllAppointmentsByDateAndBarber(Integer day, Integer month, Integer year, Long barberId);
}
