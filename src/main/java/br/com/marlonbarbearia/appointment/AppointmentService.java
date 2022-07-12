package br.com.marlonbarbearia.appointment;

import br.com.marlonbarbearia.appointment.AppointmentRequest;
import br.com.marlonbarbearia.appointment.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
	
	List<AppointmentResponse> findAllAppointments();
	AppointmentResponse findAppointmentById(Long appointmentId);
	void deleteAppointmentById(Long appointmentId);
	AppointmentResponse updateAppointment(Long appointmentId, AppointmentRequest appointment);
	Boolean isAppointmentWithoutRangeOfExistingAppointments(AppointmentRequest newAppointment);
	void createAppointment(AppointmentRequest request);
	List<AppointmentResponse> findAppointmentsByDate(Integer day, Integer month, Integer year);
	List<AppointmentResponse> findAllAppointmentsByBarber(Long barberId);
	List<AppointmentResponse> findAllAppointmentsByDateAndBarber(Integer day, Integer month, Integer year, Long barberId);
}
