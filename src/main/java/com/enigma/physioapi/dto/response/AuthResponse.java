package com.enigma.physioapi.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
  private String accessToken;
  private String refreshToken;
  private String role;
}