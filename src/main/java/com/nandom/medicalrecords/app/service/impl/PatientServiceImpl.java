package com.nandom.medicalrecords.app.service.impl;

import com.nandom.medicalrecords.app.exception.PatientNotFoundException;
import com.nandom.medicalrecords.app.model.Patient;
import com.nandom.medicalrecords.app.payload.response.ApiResponseDto;
import com.nandom.medicalrecords.app.payload.response.PatientResponseDto;
import com.nandom.medicalrecords.app.repository.PatientRepository;
import com.nandom.medicalrecords.app.service.PatientService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public ApiResponseDto addPatient(Patient patient) {
        patient.setLastVisitDate(LocalDate.now());
        Patient p = patientRepository.save(patient);

        ApiResponseDto response = new ApiResponseDto();
        PatientResponseDto patientResponseDto = new PatientResponseDto();

        PatientResponseDto responseDto = new PatientResponseDto();
        BeanUtils.copyProperties(p, patientResponseDto);

        response.setCode(HttpStatus.CREATED.value());
        response.setData(patientResponseDto);
        response.setMessage("Patient record saved successfully");
        return response;
    }

    @Override
    public ApiResponseDto findAllPatients(Pageable page) {
        Page<Patient> patientList = patientRepository.findAll(page);
        List<PatientResponseDto> patientResponseDtoList = new ArrayList<>();
        for (Patient source : patientList) {
            PatientResponseDto target = new PatientResponseDto();
            BeanUtils.copyProperties(source, target);
            patientResponseDtoList.add(target);
        }

        ApiResponseDto response = new ApiResponseDto();
        response.setMessage("List of all patients");
        response.setCode(HttpStatus.OK.value());
        response.setData(patientResponseDtoList);
        return response;
    }

    @Override
    public ApiResponseDto findAllPatientsByAgeUpto(Integer age) {
        List<Patient> patientList = patientRepository.findAllPatientsByAgeUpto(age);
        List<PatientResponseDto> patientResponseDtoList = new ArrayList<>();

        for (Patient source : patientList) {
            PatientResponseDto target = new PatientResponseDto();
            BeanUtils.copyProperties(source, target);
            patientResponseDtoList.add(target);
        }

        ApiResponseDto response = new ApiResponseDto();
        response.setMessage("List of all patients upto " + age + " year(s)");
        response.setCode(HttpStatus.OK.value());
        response.setData(patientResponseDtoList);
        return response;
    }


    @Override
    public ApiResponseDto findAllPatientsByAgeRange(LocalDate ageFrom, LocalDate ageTo) {
        List<Patient> patientList = patientRepository.findAllPatientsByAgeRange(ageFrom, ageTo);
        List<PatientResponseDto> patientResponseDtoList = new ArrayList<>();

        for (Patient source : patientList) {
            PatientResponseDto target = new PatientResponseDto();
            BeanUtils.copyProperties(source, target);
            patientResponseDtoList.add(target);
        }

        ApiResponseDto response = new ApiResponseDto();
        response.setMessage("List of all patients from " + ageFrom + " year(s) to " + ageTo + " year(s)");
        response.setCode(HttpStatus.OK.value());
        response.setData(patientResponseDtoList);
        return response;
    }


    @Override
    @Transactional
    public ApiResponseDto deletePatient(Long id) {

        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            ApiResponseDto response = new ApiResponseDto();
            patientRepository.deleteById(id);

            response.setMessage("Patient with Id " + id + "has been deleted successfully");
            response.setCode(HttpStatus.OK.value());
            response.setData(response);
            return response;
        }
        ApiResponseDto response = new ApiResponseDto();
        response.setMessage("Patient with Id " + id + " is not found");
        response.setCode(HttpStatus.NOT_FOUND.value());
        return response;
    }

    @Override
    public ApiResponseDto findPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            ApiResponseDto response = new ApiResponseDto();
            PatientResponseDto target = new PatientResponseDto();
            BeanUtils.copyProperties(patient.get(), target);
            response.setMessage("Patient with Id " + id + " has been deleted successfully");
            response.setCode(HttpStatus.OK.value());
            response.setData(target);
            return response;
        }

        ApiResponseDto response = new ApiResponseDto();
        response.setMessage("Patient with Id " + id + " is not found");
        response.setCode(HttpStatus.NOT_FOUND.value());
        return response;
    }

    @Override
    public ApiResponseDto findPatientByName(String name) {
        Optional<Patient> patient = patientRepository.findPatientByName(name);
        ApiResponseDto response = new ApiResponseDto();

        if (patient.isPresent()) {

            PatientResponseDto target = new PatientResponseDto();
            BeanUtils.copyProperties(patient.get(), target);

            response.setMessage("Patient with name " + name + " has been found");
            response.setCode(HttpStatus.OK.value());
            response.setData(target);
            return response;
        }

        response.setMessage("Patient with name " + name + " is not found");
        response.setCode(HttpStatus.NOT_FOUND.value());
        return response;
    }

    @Override
    @Transactional
    public ApiResponseDto deleteMultiplePatientsLastVisitDateBetween(LocalDate dateFrom, LocalDate dateTo) {
        int deletedRecords = patientRepository.deleteMultiplePatientsLastVisitDateBetween(dateFrom, dateTo);
        ApiResponseDto response = new ApiResponseDto();

        if (deletedRecords == 0) {
            response.setMessage("No Patient records found between " + dateFrom + " and " + dateTo);
            response.setCode(HttpStatus.OK.value());
            return response;
        }

        response.setMessage(deletedRecords + " Patient(s) records have been deleted");
        response.setCode(HttpStatus.OK.value());
        return response;
    }

    @Override
    @Transactional
    public ApiResponseDto updatePatient(Patient staff) {
        Patient patient = patientRepository.save(staff);
        ApiResponseDto response = new ApiResponseDto();
        PatientResponseDto patientResponseDto = new PatientResponseDto();
        BeanUtils.copyProperties(patient, patientResponseDto);

        response.setMessage("Patient records have been updated successfully");
        response.setCode(HttpStatus.OK.value());
        response.setData(patientResponseDto);

        return response;
    }

    @Override
    public ByteArrayInputStream downloadPatientProfile(Long id) {

        Optional<Patient> patient = patientRepository.findById(id);

        if (patient.isPresent()) {
            final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
            List<Patient> patientList = patientRepository.findPatientByIdAndReturnAsList(id);

            try (ByteArrayOutputStream out = new ByteArrayOutputStream(); CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

                for (Patient Patient : patientList) {
                    List<String> data = Arrays.asList(String.valueOf(Patient.getId()), Patient.getName(), String.valueOf(Patient.getAge()), String.valueOf(Patient.getLastVisitDate()));
                    csvPrinter.printRecord(data);
                }
                csvPrinter.flush();
                return new ByteArrayInputStream(out.toByteArray());
            } catch (IOException e) {
                throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
            }
        } else {
            throw new PatientNotFoundException("Patient with Id: " + id + " not found");
        }

    }


}
