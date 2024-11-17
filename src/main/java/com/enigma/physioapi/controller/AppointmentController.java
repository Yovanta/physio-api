package com.enigma.physioapi.controller;

import com.enigma.physioapi.constant.Constant;
import com.enigma.physioapi.dto.request.AppointmentRequest;
import com.enigma.physioapi.dto.response.AppointmentResponse;
import com.enigma.physioapi.service.AppointmentService;
import com.enigma.physioapi.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(Constant.APPOINTMENT_API)
@RequiredArgsConstructor
public class AppointmentController {

  private final AppointmentService appointmentService;

  @PostMapping
  public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest request) {
    AppointmentResponse response = appointmentService.createAppointment(request);
    return ResponseUtil.buildResponse(HttpStatus.CREATED, "Appointment created successfully", response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getAppointmentById(@PathVariable String id) {
    AppointmentResponse response = appointmentService.getAppointmentById(id);
    return ResponseUtil.buildResponse(HttpStatus.OK, "Fetched appointment successfully", response);
  }

  @GetMapping
  public ResponseEntity<?> getAllAppointments() {
    List<AppointmentResponse> responses = appointmentService.getAllAppointments();
    return ResponseUtil.buildResponse(HttpStatus.OK, "Fetched all appointments", responses);
  }

  @GetMapping("/physiotherapist/{physiotherapistId}")
  public ResponseEntity<?> getAppointmentsByPhysiotherapistAndDate(
          @PathVariable String physiotherapistId,
          @RequestParam LocalDateTime date) {
    List<AppointmentResponse> responses = appointmentService.getAppointmentsByPhysiotherapistAndDate(physiotherapistId, date);
    return ResponseUtil.buildResponse(HttpStatus.OK, "Fetched appointments for physiotherapist", responses);
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<?> updateAppointmentStatus(@PathVariable String id, @RequestParam String status) {
    AppointmentResponse response = appointmentService.updateAppointmentStatus(id, status);
    return ResponseUtil.buildResponse(HttpStatus.OK, "Appointment status updated successfully", response);
  }
}
