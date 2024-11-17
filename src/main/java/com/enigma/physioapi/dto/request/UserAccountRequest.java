package com.enigma.physioapi.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccountRequest {
  private String username;
  private String password;
  private String role;
}
