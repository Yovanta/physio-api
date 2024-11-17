package com.enigma.physioapi.dto.response;

import com.enigma.physioapi.constant.AppointmentStatus;
import com.enigma.physioapi.entity.Appointment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponse {
  private String id;
  private String appointmentId;
  private String patientId;
  private String physiotherapistId;
  private LocalDateTime appointmentDate;
  private String timeSlot;
  private AppointmentStatus status;

  public static AppointmentResponse mapToAppointmentResponse(Appointment appointment) {
    return AppointmentResponse.builder()
            .id(appointment.getId())
            .patientId(appointment.getPatient().getId())
            .physiotherapistId(appointment.getPhysiotherapist().getId())
            .appointmentDate(appointment.getAppointmentDate())
            .timeSlot(appointment.getTimeSlot())
            .status(appointment.getStatus())
            .build();
  }
}
