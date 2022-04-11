package com.nandom.medicalrecords.app.repository;

import com.nandom.medicalrecords.app.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT c FROM Patient c WHERE c.id = ?1")
    List<Patient> findPatientByIdAndReturnAsList(Long id);

    @Query("SELECT c FROM Patient c WHERE c.age <= ?1")
    List<Patient> findAllPatientsByAgeUpto(Integer age);

    @Query("SELECT c FROM Patient c WHERE c.age BETWEEN ?1 AND ?2")
    List<Patient> findAllPatientsByAgeRange(LocalDate ageFrom, LocalDate ageTo);

    Optional<Patient> findPatientByName(String name);

    @Modifying
    @Query("DELETE FROM Patient c WHERE c.lastVisitDate BETWEEN ?1 AND ?2")
    int deleteMultiplePatientsLastVisitDateBetween(LocalDate dateFrom, LocalDate dateTo);
}
