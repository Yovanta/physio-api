package com.enigma.physioapi.dto.response;

import com.enigma.physioapi.entity.UserAccount;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccountResponse {
  private String id;
  private String username;
  private String role;

  public static UserAccountResponse mapToUserAccountResponse(UserAccount userAccount) {
    return UserAccountResponse.builder()
            .id(userAccount.getId())
            .username(userAccount.getUsername())
            .role(userAccount.getRole().getDescription())
            .build();
  }
}
