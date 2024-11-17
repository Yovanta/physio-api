package com.enigma.physioapi.repository;

import com.enigma.physioapi.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, String> {
  @Query(value = "SELECT * FROM availability WHERE physiotherapist_id = :physiotherapistId AND date = :date", nativeQuery = true)
  List<Availability> findByPhysiotherapistAndDate(@Param("physiotherapistId") String physiotherapistId, @Param("date") LocalDate date);

  @Query(value = "SELECT * FROM availability WHERE physiotherapist_id = :physiotherapistId AND date = :date " +
          "AND start_time = :startTime AND end_time = :endTime", nativeQuery = true)
  Optional<Availability> findByPhysiotherapistAndDateAndTime(@Param("physiotherapistId") String physiotherapistId,
                                                             @Param("date") LocalDate date,
                                                             @Param("startTime") LocalTime startTime,
                                                             @Param("endTime") LocalTime endTime);
}

