package com.enigma.physioapi.service.impl;

import com.enigma.physioapi.constant.UserRole;
import com.enigma.physioapi.dto.request.UserAccountRequest;
import com.enigma.physioapi.dto.request.UserUpdatePasswordRequest;
import com.enigma.physioapi.dto.response.UserAccountResponse;
import com.enigma.physioapi.entity.UserAccount;
import com.enigma.physioapi.repository.UserAccountRepository;
import com.enigma.physioapi.service.UserService;
import com.enigma.physioapi.util.ValidationUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserAccountRepository userAccountRepository;
  private final PasswordEncoder passwordEncoder;
  private final ValidationUtil validationUtil;

  @Value("${physio.user-admin}")
  private String USERNAME_ADMIN;

  @Value("${physio.user-password}")
  private String PASSWORD_ADMIN;

  @PostConstruct
  public void initUser() {
    boolean isExist = userAccountRepository.existsByUsername(USERNAME_ADMIN);
    if (isExist) return;
    UserAccount userAccount = UserAccount.builder()
            .username(USERNAME_ADMIN)
            .password(passwordEncoder.encode(PASSWORD_ADMIN))
            .role(UserRole.ROLE_ADMIN)
            .build();
    userAccountRepository.saveAndFlush(userAccount);
  }


  @Transactional(rollbackFor = Exception.class)
  @Override
  public UserAccountResponse create(UserAccountRequest request) {
    validationUtil.validate(request);
    try {
      UserRole userRole = UserRole.findByDescription(request.getRole());
      if (userRole == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");

      UserAccount userAccount = UserAccount.builder()
              .username(request.getUsername())
              .password(passwordEncoder.encode(request.getPassword()))
              .role(userRole)
              .build();

      userAccountRepository.saveAndFlush(userAccount);
      return UserAccountResponse.mapToUserAccountResponse(userAccount);

    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
    }

  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public UserAccount create(UserAccount userAccount) {
    userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
    return userAccountRepository.saveAndFlush(userAccount);
  }

  @Transactional(readOnly = true)
  @Override
  public List<UserAccountResponse> getAll() {
    List<UserAccount> userAccounts = userAccountRepository.findAll();
    return userAccounts.stream().map(UserAccountResponse::mapToUserAccountResponse)
            .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  @Override
  public UserAccount getById(String id) {
    return userAccountRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
  }

  @Override
  public UserAccountResponse getAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserAccount userAccount = (UserAccount) authentication.getPrincipal();
    return UserAccountResponse.mapToUserAccountResponse(userAccount);
  }


  @Transactional(rollbackFor = Exception.class)
  @Override
  public void updatePassword(String id, UserUpdatePasswordRequest request) {
    UserAccount userAccount = getById(id);

    if (!passwordEncoder.matches(request.getCurrentPassword(), userAccount.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid current password");
    }

    userAccount.setPassword(passwordEncoder.encode(request.getNewPassword()));
    userAccountRepository.saveAndFlush(userAccount);
  }
}
