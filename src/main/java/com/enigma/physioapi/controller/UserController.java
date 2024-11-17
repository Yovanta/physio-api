package com.enigma.physioapi.controller;

import com.enigma.physioapi.constant.Constant;
import com.enigma.physioapi.dto.request.UserAccountRequest;
import com.enigma.physioapi.dto.request.UserUpdatePasswordRequest;
import com.enigma.physioapi.dto.response.UserAccountResponse;
import com.enigma.physioapi.service.UserService;
import com.enigma.physioapi.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constant.USER_ACCOUNT_API)
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<?> createUserAccount(@RequestBody UserAccountRequest request) {
    UserAccountResponse response = userService.create(request);
    return ResponseUtil.buildResponse(HttpStatus.CREATED, "User account created successfully", response);
  }

  @GetMapping
  public ResponseEntity<?> getAllUserAccounts() {
    List<UserAccountResponse> responses = userService.getAll();
    return ResponseUtil.buildResponse(HttpStatus.OK, "Fetched all user accounts", responses);
  }

  @GetMapping("/me")
  public ResponseEntity<?> getAuthenticatedUser() {
    UserAccountResponse response = userService.getAuthentication();
    return ResponseUtil.buildResponse(HttpStatus.OK, "Fetched authenticated user", response);
  }

  @PatchMapping("/{id}/password")
  public ResponseEntity<?> updateUserPassword(
          @PathVariable String id,
          @RequestBody UserUpdatePasswordRequest request) {
    userService.updatePassword(id, request);
    return ResponseUtil.buildResponse(HttpStatus.OK, "Password updated successfully", null);
  }
}
