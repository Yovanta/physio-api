package com.enigma.physioapi.dto.response;

import com.enigma.physioapi.entity.Physiotherapist;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhysiotherapistResponse {
  private String id;
  private String name;
  private String email;
  private String specialization;
  private Integer experienceYear;
  private double rating;
  private String userId;

  public static PhysiotherapistResponse mapToPhysiotherapistResponse(Physiotherapist physiotherapist) {
    return PhysiotherapistResponse.builder()
            .id(physiotherapist.getId())
            .name(physiotherapist.getName())
            .email(physiotherapist.getEmail())
            .specialization(physiotherapist.getSpecialization())
            .experienceYear(physiotherapist.getExperienceYear())
            .rating(physiotherapist.getRating())
            .userId(physiotherapist.getUserAccount().getId())
            .build();
  }
}
