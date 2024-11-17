package com.enigma.physioapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdatePasswordRequest {
  @NotBlank(message = "current password is required")
  private String currentPassword;

  @NotBlank(message = "new password is required")
  private String newPassword;
}
