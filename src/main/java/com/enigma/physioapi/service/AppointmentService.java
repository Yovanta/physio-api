package com.enigma.physioapi.service;

import com.enigma.physioapi.dto.request.AppointmentRequest;
import com.enigma.physioapi.dto.response.AppointmentResponse;
import com.enigma.physioapi.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
  AppointmentResponse createAppointment(AppointmentRequest request);

  Appointment getOne(String id);

  AppointmentResponse getAppointmentById(String id);

  List<AppointmentResponse> getAllAppointments();

  List<AppointmentResponse> getAppointmentsByPhysiotherapistAndDate(String physiotherapistId, LocalDateTime date);

  AppointmentResponse updateAppointmentStatus(String id, String status);
}
