package com.enigma.physioapi.repository;

import com.enigma.physioapi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
  @Query(value = "SELECT * FROM patient WHERE email = :email", nativeQuery = true)
  Optional<Patient> findByEmail(@Param("email") String email);

  @Query(value = "SELECT * FROM patient WHERE name LIKE %:name%", nativeQuery = true)
  List<Patient> findByNameContaining(@Param("name") String name);

}
