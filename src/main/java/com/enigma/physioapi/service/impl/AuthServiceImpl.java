package com.enigma.physioapi.service.impl;

import com.enigma.physioapi.dto.request.AuthRequest;
import com.enigma.physioapi.dto.response.AuthResponse;
import com.enigma.physioapi.entity.UserAccount;
import com.enigma.physioapi.service.AuthService;
import com.enigma.physioapi.service.JwtService;
import com.enigma.physioapi.service.RefreshTokenService;
import com.enigma.physioapi.service.UserService;
import com.enigma.physioapi.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserService userService;
  private final RefreshTokenService refreshTokenService;
  private final ValidationUtil validationUtil;

  @Override
  public AuthResponse login(AuthRequest request) {
    validationUtil.validate(request);
    Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authenticate);

    UserAccount userAccount = (UserAccount) authenticate.getPrincipal();

    String refreshToken = refreshTokenService.createToken(userAccount.getId());
    String accessToken = jwtService.generateAccessToken(userAccount);

    return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .role(userAccount.getRole().getDescription())
            .build();

  }

  @Override
  public AuthResponse refreshToken(String token) {
    String userId = refreshTokenService.getUserIdByToken(token);
    UserAccount userAccount = userService.getById(userId);

    String newRefreshToken = refreshTokenService.rotateRefreshToken(userId);
    String newAccessToken = jwtService.generateAccessToken(userAccount);

    return AuthResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(newRefreshToken)
            .role(userAccount.getRole().getDescription())
            .build();
  }

  @Override
  public void logout(String accessToken) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserAccount userAccount = (UserAccount) authentication.getPrincipal();

    refreshTokenService.deleteRefreshToken(userAccount.getId());
    jwtService.blacklistAccessToken(accessToken);
  }
}
