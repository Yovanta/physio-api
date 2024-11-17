package com.enigma.physioapi.constant;

import lombok.Getter;

@Getter
public enum UserRole {
  ROLE_ADMIN("Admin"),
  ROLE_PHYSIOTHERAPIST("Physiotherapist"),
  ROLE_PATIENT("Patient");

  private final String description;

  UserRole(String description) {
    this.description = description;
  }

  public static UserRole findByDescription(String description) {
    for (UserRole value : values()) {
      if (value.description.equalsIgnoreCase(description)) {
        return value;
      }
    }
    return null;
  }
}
