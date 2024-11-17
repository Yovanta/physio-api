package com.enigma.physioapi.controller;

import com.enigma.physioapi.constant.Constant;
import com.enigma.physioapi.dto.request.PhysiotherapistRequest;
import com.enigma.physioapi.dto.response.PhysiotherapistResponse;
import com.enigma.physioapi.service.PhysiotherapistService;
import com.enigma.physioapi.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constant.PHYSIOTHERAPIST_API)
@RequiredArgsConstructor
public class PhysiotherapistController {

  private final PhysiotherapistService physiotherapistService;

  @PostMapping
  public ResponseEntity<?> createPhysiotherapist(@RequestBody PhysiotherapistRequest request) {
    PhysiotherapistResponse response = physiotherapistService.create(request);
    return ResponseUtil.buildResponse(HttpStatus.CREATED, "Physiotherapist created successfully", response);
  }

  @GetMapping
  public ResponseEntity<?> getAllPhysiotherapists() {
    List<PhysiotherapistResponse> responses = physiotherapistService.getAll();
    return ResponseUtil.buildResponse(HttpStatus.OK, "Fetched all physiotherapists", responses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPhysiotherapistById(@PathVariable String id) {
    PhysiotherapistResponse response = physiotherapistService.getById(id);
    return ResponseUtil.buildResponse(HttpStatus.OK, "Fetched physiotherapist details", response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePhysiotherapist(@PathVariable String id, @RequestBody PhysiotherapistRequest request) {
    PhysiotherapistResponse response = physiotherapistService.update(id, request);
    return ResponseUtil.buildResponse(HttpStatus.OK, "Physiotherapist updated successfully", response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePhysiotherapist(@PathVariable String id) {
    physiotherapistService.delete(id);
    return ResponseUtil.buildResponse(HttpStatus.NO_CONTENT, "Physiotherapist deleted successfully", null);
  }

  @GetMapping("/specialization")
  public ResponseEntity<?> getPhysiotherapistsBySpecialization(@RequestParam String specialization) {
    List<PhysiotherapistResponse> responses = physiotherapistService.getPhysiotherapistsBySpecialization(specialization);
    return ResponseUtil.buildResponse(HttpStatus.OK, "Fetched physiotherapists by specialization", responses);
  }
}
