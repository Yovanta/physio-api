package com.enigma.physioapi.service;

import com.enigma.physioapi.dto.request.UserAccountRequest;
import com.enigma.physioapi.dto.request.UserUpdatePasswordRequest;
import com.enigma.physioapi.dto.response.UserAccountResponse;
import com.enigma.physioapi.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
  UserAccountResponse create(UserAccountRequest request);

  UserAccount create(UserAccount userAccount);

  List<UserAccountResponse> getAll();

  UserAccount getById(String id);

  UserAccountResponse getAuthentication();

  void updatePassword(String id, UserUpdatePasswordRequest request);

  UserDetails loadUserByUsername(String username);

}
