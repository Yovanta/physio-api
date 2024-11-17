package com.enigma.physioapi.repository;

import com.enigma.physioapi.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {
  @Query(value = "SELECT * FROM m_appointments WHERE physiotherapist_id = :physiotherapistId AND appointment_date = :date", nativeQuery = true)
  List<Appointment> findAppointmentsByPhysiotherapistAndDate(@Param("physiotherapistId") String physiotherapistId,
                                                             @Param("date") LocalDateTime date);
}
