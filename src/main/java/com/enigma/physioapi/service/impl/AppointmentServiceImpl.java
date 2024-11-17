package com.enigma.physioapi.service.impl;

import com.enigma.physioapi.constant.AppointmentStatus;
import com.enigma.physioapi.dto.request.AppointmentRequest;
import com.enigma.physioapi.dto.response.AppointmentResponse;
import com.enigma.physioapi.entity.Appointment;
import com.enigma.physioapi.entity.Patient;
import com.enigma.physioapi.entity.Physiotherapist;
import com.enigma.physioapi.repository.AppointmentRepository;
import com.enigma.physioapi.service.AppointmentService;
import com.enigma.physioapi.service.PatientService;
import com.enigma.physioapi.service.PhysiotherapistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
  private AppointmentRepository appointmentRepository;
  private PatientService patientService;
  private PhysiotherapistService physiotherapistService;

  @Override
  public AppointmentResponse createAppointment(AppointmentRequest request) {
    Patient patient = patientService.getOne(request.getPatientId());
    Physiotherapist physiotherapist = physiotherapistService.getOne(request.getPhysiotherapistId());

    Appointment appointment = Appointment.builder()
            .appointmentDate(request.getAppointmentDate())
            .timeSlot(request.getTimeSlot())
            .patient(patient)
            .physiotherapist(physiotherapist)
            .status(AppointmentStatus.BOOKED)
            .build();

    Appointment savedAppointment = appointmentRepository.save(appointment);

    return AppointmentResponse.mapToAppointmentResponse(savedAppointment);
  }

  @Override
  public Appointment getOne(String id) {
    return appointmentRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));
  }

  @Override
  public AppointmentResponse getAppointmentById(String id) {
    Appointment appointment = getOne(id);
    return AppointmentResponse.mapToAppointmentResponse(appointment);
  }

  @Override
  public List<AppointmentResponse> getAllAppointments() {
    List<Appointment> appointments = appointmentRepository.findAll();
    return appointments.stream().map(AppointmentResponse::mapToAppointmentResponse).collect(Collectors.toList());
  }

  @Override
  public List<AppointmentResponse> getAppointmentsByPhysiotherapistAndDate(String physiotherapistId, LocalDateTime date) {
    List<Appointment> appointments = appointmentRepository.findAppointmentsByPhysiotherapistAndDate(physiotherapistId, date);
    return appointments.stream().map(AppointmentResponse::mapToAppointmentResponse).collect(Collectors.toList());
  }

  @Override
  public AppointmentResponse updateAppointmentStatus(String id, String status) {
    Appointment appointment = getOne(id);
    appointment.setStatus(AppointmentStatus.findByDescription(status));
    appointment = appointmentRepository.save(appointment);
    return AppointmentResponse.mapToAppointmentResponse(appointment);
  }


}
