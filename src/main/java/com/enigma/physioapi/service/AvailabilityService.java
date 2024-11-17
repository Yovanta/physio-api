package com.enigma.physioapi.service;

import com.enigma.physioapi.dto.request.AvailabilityRequest;
import com.enigma.physioapi.dto.response.AvailabilityResponse;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {
  AvailabilityResponse addAvailability(AvailabilityRequest request);

  List<AvailabilityResponse> getAvailabilitiesByPhysiotherapist(String physiotherapistId, LocalDate date);

  AvailabilityResponse getAvailabilityByPhysiotherapistAndDateAndTime(String physiotherapistId,
                                                                      LocalDate date,
                                                                      String startTime,
                                                                      String endTime);
}
