package com.enigma.physioapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRequest {
  @NotBlank(message = "username is required")
  private String username;

  @NotBlank(message = "password is required")
  private String password;

  @NotBlank(message = "name is required")
  private String name;

  @NotBlank(message = "email is required")
  private String email;

  @NotBlank(message = "phone number is required")
  private String phoneNumber;

}
