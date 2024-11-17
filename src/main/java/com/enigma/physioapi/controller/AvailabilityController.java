package com.enigma.physioapi.controller;

import com.enigma.physioapi.constant.Constant;
import com.enigma.physioapi.dto.request.AvailabilityRequest;
import com.enigma.physioapi.dto.response.AvailabilityResponse;
import com.enigma.physioapi.service.AvailabilityService;
import com.enigma.physioapi.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(Constant.AVAILABILITY_API)
@RequiredArgsConstructor
public class AvailabilityController {

  private final AvailabilityService availabilityService;

  @PostMapping
  public ResponseEntity<?> addAvailability(@RequestBody AvailabilityRequest request) {
    AvailabilityResponse response = availabilityService.addAvailability(request);
    return ResponseUtil.buildResponse(HttpStatus.CREATED, "Availability added successfully", response);
  }

  @GetMapping("/{physiotherapistId}")
  public ResponseEntity<?> getAvailabilitiesByPhysiotherapist(
          @PathVariable String physiotherapistId,
          @RequestParam LocalDate date) {
    List<AvailabilityResponse> responses = availabilityService.getAvailabilitiesByPhysiotherapist(physiotherapistId, date);
    return ResponseUtil.buildResponse(HttpStatus.OK, "Fetched availabilities for physiotherapist", responses);
  }

  @GetMapping("/{physiotherapistId}/specific")
  public ResponseEntity<?> getAvailabilityByPhysiotherapistAndDateAndTime(
          @PathVariable String physiotherapistId,
          @RequestParam LocalDate date,
          @RequestParam String startTime,
          @RequestParam String endTime) {
    AvailabilityResponse response = availabilityService.getAvailabilityByPhysiotherapistAndDateAndTime(physiotherapistId, date, startTime, endTime);
    return ResponseUtil.buildResponse(HttpStatus.OK, "Fetched specific availability", response);
  }
}
