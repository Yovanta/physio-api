package com.enigma.physioapi.service;

import com.enigma.physioapi.dto.request.PhysiotherapistRequest;
import com.enigma.physioapi.dto.response.PhysiotherapistResponse;
import com.enigma.physioapi.entity.Physiotherapist;

import java.util.List;

public interface PhysiotherapistService {
  PhysiotherapistResponse create(PhysiotherapistRequest request);

  Physiotherapist getOne(String id);

  List<PhysiotherapistResponse> getAll();

  PhysiotherapistResponse getById(String id);

  PhysiotherapistResponse update(String id, PhysiotherapistRequest request);

  void delete(String id);

  List<PhysiotherapistResponse> getPhysiotherapistsBySpecialization(String specialization);
}
