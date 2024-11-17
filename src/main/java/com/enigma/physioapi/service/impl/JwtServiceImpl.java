package com.enigma.physioapi.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.enigma.physioapi.entity.UserAccount;
import com.enigma.physioapi.service.JwtService;
import com.enigma.physioapi.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {
  @Value("${physio.jwt-secret}")
  private String SECRET_KEY;
  @Value("${physio.jwt-expiration-in-minutes}")
  private Long EXPIRATION_IN_MINUTES;
  @Value("${physio.jwt-issuer}")
  private String ISSUER;

  private final String BLACKLISTED = "BLACKLISTED";

  private final RedisService redisService;

  @Override
  public String generateAccessToken(UserAccount userAccount) {
    log.info("Generating JWT Token for User: {}", userAccount.getId());
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
      return JWT.create()
              .withIssuer(ISSUER)
              .withIssuedAt(Instant.now())
              .withExpiresAt(Instant.now().plus(EXPIRATION_IN_MINUTES, ChronoUnit.MINUTES))
              .withSubject(userAccount.getId())
              .withClaim("role", userAccount.getRole().getDescription())
              .sign(algorithm);
    } catch (JWTCreationException e) {
      log.error("Error Creating JWT Token: {}", e.getMessage());
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error create token");
    }
  }

  @Override
  public String getUserId(String token) {
    DecodedJWT decodedJWT = extractClaimJWT(token);
    if (decodedJWT != null)
      return decodedJWT.getSubject();
    return null;
  }

  @Override
  public String extractTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    return parseToken(bearerToken);
  }

  @Override
  public void blacklistAccessToken(String bearerToken) {
    String token = parseToken(bearerToken);

    DecodedJWT decodedJWT = extractClaimJWT(token);
    if (decodedJWT == null)
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error invalid token");

    Date expiresAt = decodedJWT.getExpiresAt();
    long timeLeft = (expiresAt.getTime() - System.currentTimeMillis());

    redisService.save(token, BLACKLISTED, Duration.ofMillis(timeLeft));
  }

  @Override
  public boolean isTokenBlacklisted(String token) {
    String blacklistToken = redisService.get(token);
    return blacklistToken != null && blacklistToken.equals(BLACKLISTED);
  }

  private DecodedJWT extractClaimJWT(String token) {
    log.info("Extract JWT Token: {}", System.currentTimeMillis());
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
      JWTVerifier verifier = JWT.require(algorithm)
              .withIssuer(ISSUER)
              .build();
      return verifier.verify(token);
    } catch (JWTVerificationException e) {
      log.error("Error While Extracting JWT Token: {}", e.getMessage());
      return null;
    }
  }

  private String parseToken(String bearerToken) {
    if (bearerToken != null && bearerToken.startsWith("Bearer "))
      return bearerToken.substring(7);
    return null;
  }
}