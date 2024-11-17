package com.enigma.physioapi.entity;

import com.enigma.physioapi.constant.Constant;
import com.enigma.physioapi.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = Constant.USER_ACCOUNT_TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccount implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(unique = true, nullable = false, length = 30)
  private String username;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  private UserRole role;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<UserRole> myRoles = List.of(role);
    return myRoles.stream()
            .map(userRole -> new SimpleGrantedAuthority(userRole.name()))
            .toList();
  }

}
