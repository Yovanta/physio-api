package com.enigma.physioapi.constant;

import lombok.Getter;

@Getter
public enum AppointmentStatus {
  BOOKED("Booked"),
  CANCELLED("Cancelled"),
  COMPLETED("Complete");

  private final String description;

  AppointmentStatus(String description) {
    this.description = description;
  }

  public static AppointmentStatus findByDescription(String description) {
    for (AppointmentStatus value : values()) {
      if (value.description.equalsIgnoreCase(description)) {
        return value;
      }
    }
    return null;
  }
}
