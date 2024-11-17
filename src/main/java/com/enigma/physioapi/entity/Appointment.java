package com.enigma.physioapi.entity;

import com.enigma.physioapi.constant.AppointmentStatus;
import com.enigma.physioapi.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = Constant.APPOINTMENT_TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false)
  private LocalDateTime appointmentDate;

  @Column(nullable = false)
  private String timeSlot;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private AppointmentStatus status;

  @ManyToOne
  private Patient patient;

  @ManyToOne
  private Physiotherapist physiotherapist;
}
