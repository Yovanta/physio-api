package com.enigma.physioapi.service.impl;

import com.enigma.physioapi.dto.request.AvailabilityRequest;
import com.enigma.physioapi.dto.response.AvailabilityResponse;
import com.enigma.physioapi.entity.Availability;
import com.enigma.physioapi.entity.Physiotherapist;
import com.enigma.physioapi.repository.AvailabilityRepository;
import com.enigma.physioapi.repository.PhysiotherapistRepository;
import com.enigma.physioapi.service.AvailabilityService;
import com.enigma.physioapi.service.PhysiotherapistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {
  private final AvailabilityRepository availabilityRepository;
  private final PhysiotherapistService physiotherapistService;

  @Override
  public AvailabilityResponse addAvailability(AvailabilityRequest request) {
    Physiotherapist physiotherapist = physiotherapistService.getOne(request.getPhysiotherapistId());

    Availability availability = Availability.builder()
            .date(request.getDate())
            .startTime(request.getStartTime())
            .endTime(request.getEndTime())
            .isBooked(false)
            .physiotherapist(physiotherapist)
            .build();

    availability = availabilityRepository.save(availability);
    return AvailabilityResponse.mapToAvailabilityResponse(availability);
  }

  @Override
  public List<AvailabilityResponse> getAvailabilitiesByPhysiotherapist(String physiotherapistId, LocalDate date) {
    List<Availability> availabilities = availabilityRepository.findByPhysiotherapistAndDate(physiotherapistId, date);
    return availabilities.stream().map(AvailabilityResponse::mapToAvailabilityResponse).collect(Collectors.toList());
  }

  @Override
  public AvailabilityResponse getAvailabilityByPhysiotherapistAndDateAndTime(String physiotherapistId, LocalDate date, String startTime, String endTime) {
    Optional<Availability> availability = availabilityRepository.findByPhysiotherapistAndDateAndTime(physiotherapistId, date, LocalTime.parse(startTime), LocalTime.parse(endTime));
    if (availability.isPresent()) {
      return AvailabilityResponse.mapToAvailabilityResponse(availability.get());
    }
    throw new RuntimeException("Availability not found");
  }
}
