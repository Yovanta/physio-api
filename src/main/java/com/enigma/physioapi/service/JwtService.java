package com.enigma.physioapi.service;

import com.enigma.physioapi.entity.UserAccount;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
  String generateAccessToken(UserAccount userAccount);

  String getUserId(String token);

  String extractTokenFromRequest(HttpServletRequest request);

  void blacklistAccessToken(String bearerToken);

  boolean isTokenBlacklisted(String token);

}
