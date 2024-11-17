package com.enigma.physioapi.controller;

import com.enigma.physioapi.constant.Constant;
import com.enigma.physioapi.dto.request.PatientRequest;
import com.enigma.physioapi.dto.response.PatientResponse;
import com.enigma.physioapi.service.PatientService;
import com.enigma.physioapi.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = Constant.PATIENT_API)
@RequiredArgsConstructor
public class PatientController {
  private final PatientService patientService;

  @PostMapping
  public ResponseEntity<?> createPatient(@RequestBody PatientRequest request) {
    PatientResponse response = patientService.create(request);
    return ResponseUtil.buildResponse(HttpStatus.CREATED, "Patient created successfully", response);
  }

  @GetMapping
  public ResponseEntity<?> getAllPatients() {
    List<PatientResponse> responses = patientService.getAll();
    return ResponseUtil.buildResponse(HttpStatus.OK, "Fetched all patients", responses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPatientById(@PathVariable String id) {
    PatientResponse response = patientService.getById(id);
    return ResponseUtil.buildResponse(HttpStatus.OK, "Fetched patient details", response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePatient(@PathVariable String id, @RequestBody PatientRequest request) {
    PatientResponse response = patientService.update(id, request);
    return ResponseUtil.buildResponse(HttpStatus.OK, "Patient updated successfully", response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePatient(@PathVariable String id) {
    patientService.delete(id);
    return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "Patient deleted successfully", null);
  }
}
