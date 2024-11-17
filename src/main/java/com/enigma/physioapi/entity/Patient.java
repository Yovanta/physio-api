package com.enigma.physioapi.entity;

import com.enigma.physioapi.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = Constant.PATIENT_TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(name = "phone_number", unique = true, length = 15)
  private String phoneNumber;

  @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
  private List<Appointment> appointments;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_account_id")
  private UserAccount userAccount;

}
