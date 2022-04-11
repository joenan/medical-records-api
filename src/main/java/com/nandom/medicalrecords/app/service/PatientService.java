package com.nandom.medicalrecords.app.service;

import com.nandom.medicalrecords.app.model.Patient;
import com.nandom.medicalrecords.app.payload.response.ApiResponseDto;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

public interface PatientService {
    ApiResponseDto addPatient(Patient patient);
    ApiResponseDto findAllPatients(Pageable page);
    ApiResponseDto findAllPatientsByAgeUpto(Integer age);
    ApiResponseDto findAllPatientsByAgeRange(LocalDate dateFrom, LocalDate dateTo);
    ApiResponseDto deletePatient(Long id);
    ApiResponseDto findPatientById(Long id);
    ApiResponseDto findPatientByName(String name);
    ApiResponseDto deleteMultiplePatientsLastVisitDateBetween(LocalDate dateFrom, LocalDate dateTo);
    ApiResponseDto updatePatient(Patient staff);
    ByteArrayInputStream downloadPatientProfile(Long id);

}
