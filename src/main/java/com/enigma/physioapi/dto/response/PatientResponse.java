package com.enigma.physioapi.dto.response;

import com.enigma.physioapi.entity.Appointment;
import com.enigma.physioapi.entity.Patient;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponse {
  private String id;
  private String name;
  private String email;
  private String phoneNumber;
  private String userId;

  public static PatientResponse mapToPatientResponse(Patient patient) {
    return PatientResponse.builder()
            .id(patient.getId())
            .name(patient.getName())
            .email(patient.getEmail())
            .phoneNumber(patient.getPhoneNumber())
            .userId(patient.getUserAccount().getId())
            .build();
  }
}
