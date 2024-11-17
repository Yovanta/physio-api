package com.enigma.physioapi.service.impl;

import com.enigma.physioapi.constant.UserRole;
import com.enigma.physioapi.dto.request.PatientRequest;
import com.enigma.physioapi.dto.response.PatientResponse;
import com.enigma.physioapi.entity.Patient;
import com.enigma.physioapi.entity.UserAccount;
import com.enigma.physioapi.repository.PatientRepository;
import com.enigma.physioapi.service.PatientService;
import com.enigma.physioapi.service.UserService;
import com.enigma.physioapi.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
  private final PatientRepository patientRepository;
  private final UserService userService;
  private final ValidationUtil validationUtil;

  @Override
  public PatientResponse create(PatientRequest request) {
    validationUtil.validate(request);
    UserAccount userAccount = UserAccount.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .role(UserRole.ROLE_PATIENT)
            .build();
    userService.create(userAccount);

    Patient patient = Patient.builder()
            .name(request.getName())
            .email(request.getEmail())
            .phoneNumber(request.getPhoneNumber())
            .userAccount(userAccount)
            .build();

    patientRepository.saveAndFlush(patient);
    return PatientResponse.mapToPatientResponse(patient);
  }

  @Override
  public Patient getOne(String id) {
    return patientRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "patient not found"));
  }

  @Override
  public List<PatientResponse> getAll() {
    return patientRepository.findAll().stream()
            .map(PatientResponse::mapToPatientResponse)
            .collect(Collectors.toList());
  }

  @Override
  public PatientResponse getById(String id) {
    return PatientResponse.mapToPatientResponse(getOne(id));
  }

  @Override
  public PatientResponse update(String id, PatientRequest request) {
    Patient patient = getOne(id);
    patient.setName(request.getName());
    patient.setEmail(request.getEmail());
    patient.setPhoneNumber(request.getPhoneNumber());

    patient = patientRepository.save(patient);
    return PatientResponse.mapToPatientResponse(patient);
  }

  @Override
  public void delete(String id) {
    patientRepository.deleteById(id);
  }
}
