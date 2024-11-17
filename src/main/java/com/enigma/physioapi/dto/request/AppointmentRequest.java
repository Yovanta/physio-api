package com.enigma.physioapi.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequest {
  private String patientId;
  private String physiotherapistId;
  private LocalDateTime appointmentDate;
  private String timeSlot;
}
