package com.enigma.physioapi.entity;

import com.enigma.physioapi.constant.Constant;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = Constant.PHYSIOTHERAPIST_TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Physiotherapist {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(nullable = false)
  private String specialization;

  @Column(nullable = false)
  private Integer experienceYear;

  private double rating;

  @OneToMany(mappedBy = "physiotherapist", cascade = CascadeType.ALL)
  private List<Availability> availabilities;

  @OneToMany(mappedBy = "physiotherapist", cascade = CascadeType.ALL)
  private List<Appointment> appointments;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_account_id")
  private UserAccount userAccount;
}
