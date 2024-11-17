package com.enigma.physioapi.service;

import com.enigma.physioapi.dto.request.PatientRequest;
import com.enigma.physioapi.dto.response.PatientResponse;
import com.enigma.physioapi.entity.Patient;

import java.util.List;

public interface PatientService {
  PatientResponse create(PatientRequest request);

  Patient getOne(String id);

  List<PatientResponse> getAll();

  PatientResponse getById(String id);

  PatientResponse update(String id, PatientRequest request);

  void delete(String id);
}
