package com.enigma.physioapi.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailabilityRequest {
  private String physiotherapistId;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
}
