package com.enigma.physioapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhysiotherapistRequest {
  @NotBlank(message = "username is required")
  private String username;

  @NotBlank(message = "password is required")
  private String password;
  private String name;
  private String email;
  private String specialization;
  private Integer experienceYear;
}
