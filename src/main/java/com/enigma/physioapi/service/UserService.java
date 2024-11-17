package com.enigma.physioapi.service;

import com.enigma.physioapi.dto.request.UserAccountRequest;
import com.enigma.physioapi.dto.request.UserUpdatePasswordRequest;
import com.enigma.physioapi.dto.response.UserAccountResponse;
import com.enigma.physioapi.entity.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserService {
  UserAccountResponse create(UserAccountRequest request);

  UserAccount create(UserAccount userAccount);

  List<UserAccountResponse> getAll();

  UserAccount getById(String id);

  UserAccountResponse getAuthentication();

  void updatePassword(String id, UserUpdatePasswordRequest request);

}
