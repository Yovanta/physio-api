package com.enigma.physioapi.repository;

import com.enigma.physioapi.entity.Physiotherapist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysiotherapistRepository extends JpaRepository<Physiotherapist, String> {
  @Query(value = "SELECT * FROM m_physiotherapist WHERE specialization = :specialization", nativeQuery = true)
  List<Physiotherapist> findBySpecialization(@Param("specialization") String specialization);
}
