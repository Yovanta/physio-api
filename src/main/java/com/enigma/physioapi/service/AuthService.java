package com.enigma.physioapi.service;

import com.enigma.physioapi.dto.request.AuthRequest;
import com.enigma.physioapi.dto.response.AuthResponse;

public interface AuthService {
  AuthResponse login(AuthRequest request);

  AuthResponse refreshToken(String token);

  void logout(String accessToken);

}
