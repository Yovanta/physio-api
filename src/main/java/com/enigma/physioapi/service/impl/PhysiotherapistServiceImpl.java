package com.enigma.physioapi.service.impl;

import com.enigma.physioapi.constant.UserRole;
import com.enigma.physioapi.dto.request.PhysiotherapistRequest;
import com.enigma.physioapi.dto.response.PhysiotherapistResponse;
import com.enigma.physioapi.entity.Physiotherapist;
import com.enigma.physioapi.entity.UserAccount;
import com.enigma.physioapi.repository.PhysiotherapistRepository;
import com.enigma.physioapi.service.PhysiotherapistService;
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
public class PhysiotherapistServiceImpl implements PhysiotherapistService {
  private final PhysiotherapistRepository physiotherapistRepository;
  private final UserService userService;
  private final ValidationUtil validationUtil;

  @Override
  public PhysiotherapistResponse create(PhysiotherapistRequest request) {
    validationUtil.validate(request);
    UserAccount userAccount = UserAccount.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .role(UserRole.ROLE_PHYSIOTHERAPIST)
            .build();
    userService.create(userAccount);

    Physiotherapist physiotherapist = Physiotherapist.builder()
            .name(request.getName())
            .email(request.getEmail())
            .specialization(request.getSpecialization())
            .experienceYear(request.getExperienceYear())
            .userAccount(userAccount)
            .build();

    physiotherapistRepository.saveAndFlush(physiotherapist);
    return PhysiotherapistResponse.mapToPhysiotherapistResponse(physiotherapist);

  }

  @Override
  public Physiotherapist getOne(String id) {
    return physiotherapistRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "physiotherapist not found"));

  }

  @Override
  public List<PhysiotherapistResponse> getAll() {
    return physiotherapistRepository.findAll().stream()
            .map(PhysiotherapistResponse::mapToPhysiotherapistResponse)
            .collect(Collectors.toList());
  }

  @Override
  public PhysiotherapistResponse getById(String id) {
    return PhysiotherapistResponse.mapToPhysiotherapistResponse(getOne(id));
  }

  @Override
  public PhysiotherapistResponse update(String id, PhysiotherapistRequest request) {
    Physiotherapist physiotherapist = getOne(id);
    physiotherapist.setName(request.getName());
    physiotherapist.setEmail(request.getEmail());
    physiotherapist.setExperienceYear(request.getExperienceYear());
    physiotherapist.setSpecialization(request.getSpecialization());

    physiotherapist = physiotherapistRepository.save(physiotherapist);
    return PhysiotherapistResponse.mapToPhysiotherapistResponse(physiotherapist);
  }

  @Override
  public void delete(String id) {
    physiotherapistRepository.deleteById(id);
  }

  @Override
  public List<PhysiotherapistResponse> getPhysiotherapistsBySpecialization(String specialization) {
    List<Physiotherapist> physiotherapists = physiotherapistRepository.findBySpecialization(specialization);
    return physiotherapists.stream().map(PhysiotherapistResponse::mapToPhysiotherapistResponse).collect(Collectors.toList());
  }
}
