package com.enigma.physioapi.dto.response;

import com.enigma.physioapi.entity.Appointment;
import com.enigma.physioapi.entity.Availability;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailabilityResponse {
  private String id;
  private String physiotherapist;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private boolean isBooked;

  public static AvailabilityResponse mapToAvailabilityResponse(Availability availability) {
    return AvailabilityResponse.builder()
            .id(availability.getId())
            .physiotherapist(availability.getPhysiotherapist().getName())
            .date(availability.getDate())
            .startTime(availability.getStartTime())
            .endTime(availability.getEndTime())
            .isBooked(availability.isBooked())
            .build();
  }
}
