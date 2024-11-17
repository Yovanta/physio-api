package com.enigma.physioapi.repository;

import com.enigma.physioapi.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
  @Query(value = "SELECT * FROM user_account WHERE username = :username", nativeQuery = true)
  Optional<UserAccount> findByUsername(@Param("username") String username);
  boolean existsByUsername(String username);
}