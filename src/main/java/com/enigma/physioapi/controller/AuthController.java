package com.enigma.physioapi.controller;

import com.enigma.physioapi.constant.Constant;
import com.enigma.physioapi.dto.request.AuthRequest;
import com.enigma.physioapi.dto.response.AuthResponse;
import com.enigma.physioapi.service.AuthService;
import com.enigma.physioapi.util.ResponseUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constant.AUTH_API)
public class AuthController {
  @Value("${physio.refresh-token-expiration-in-hours}")
  private Integer REFRESH_TOKEN_EXPIRY;

  private final AuthService authService;


  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response) {
    AuthResponse login = authService.login(request);
    setCookie(response, login.getRefreshToken());
    return ResponseUtil.buildResponse(HttpStatus.OK, "Login successfully", login);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = getRefreshTokenFromCookie(request);

    AuthResponse authResponse = authService.refreshToken(refreshToken);
    setCookie(response, authResponse.getRefreshToken());
    return ResponseUtil.buildResponse(HttpStatus.OK, "Success refresh token", authResponse);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletRequest request) {
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    authService.logout(bearerToken);
    return ResponseUtil.buildResponse(HttpStatus.OK, "Successfully logout", null);
  }

  private String getRefreshTokenFromCookie(HttpServletRequest request) {
    Cookie cookie = Arrays.stream(request.getCookies())
            .filter(c -> c.getName().equals(Constant.REFRESH_TOKEN_COOKIE_NAME))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh Token is required"));
    return cookie.getValue();
  }

  private void setCookie(HttpServletResponse response, String refreshToken) {
    Cookie cookie = new Cookie(Constant.REFRESH_TOKEN_COOKIE_NAME, refreshToken);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60 * REFRESH_TOKEN_EXPIRY);
    response.addCookie(cookie);
  }
}