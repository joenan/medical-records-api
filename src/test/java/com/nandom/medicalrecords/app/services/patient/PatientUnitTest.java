package com.nandom.medicalrecords.app.services.patient;


import com.nandom.medicalrecords.app.model.Patient;
import com.nandom.medicalrecords.app.payload.response.ApiResponseDto;
import com.nandom.medicalrecords.app.repository.PatientRepository;
import com.nandom.medicalrecords.app.service.PatientService;
import com.nandom.medicalrecords.app.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientUnitTest {

    @Mock
    private PatientRepository patientRepository;

    PatientService patientService;

    @BeforeEach
    void initUseCase() {
        patientService = new PatientServiceImpl(patientRepository);
    }

    @Test
    public void savedPatient_then_return_Success() throws NoSuchFieldException, IllegalAccessException {
        //Given
        Patient patient = new Patient("Samson", 30, LocalDate.now());

        // When
        when(patientRepository.save(patient)).thenReturn(patient);

        //Then
        ApiResponseDto response = patientService.addPatient(patient);

        ArgumentCaptor<Patient> staffCapture = ArgumentCaptor.forClass(Patient.class);
        verify(patientRepository, Mockito.times(1)).save(staffCapture.capture());

        Patient expectedStaff = staffCapture.getValue();

        //Get field names from StaffResponseDto
        Field[] myFields = response.getData().getClass().getDeclaredFields();

        Assertions.assertEquals(expectedStaff.getName(), myFields[1].get(response.getData()));
        Assertions.assertEquals(expectedStaff.getAge(), myFields[2].get(response.getData()));
        assertThat(response.getCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getMessage()).isEqualTo("Patient record saved successfully");
    }


    @Test
    public void getAllPatientsListTest() throws IllegalAccessException {
        int page = 1;
        int size = 10;

        List<Patient> list = new ArrayList<>();
        Patient patientOne = new Patient("Jonah", 20, LocalDate.now());
        Patient patientTwo = new Patient("Mohammed", 22, LocalDate.now());
        Patient patientThree = new Patient("Fred", 35, LocalDate.now());

        list.add(patientOne);
        list.add(patientTwo);
        list.add(patientThree);

        //When
        when(patientRepository.findAll()).thenReturn(list);

        //then
        Pageable paging = PageRequest.of(page, size);
        ApiResponseDto response = patientService.findAllPatients(paging);

        //Get field names from StaffResponseDto
        Field[] myFields = response.getData().getClass().getDeclaredFields();

        assertThat(response.getCode()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("List of all patients");
    }

    @Test
    public void when_deleteStaff_should_return_success()  {
        //Given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Mustafa Tarek");
        patient.setAge(25);
        patient.setLastVisitDate(LocalDate.now());

        //When
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));

        // Then
        doNothing().when(patientRepository).deleteById(patient.getId());

        ApiResponseDto response = patientService.deletePatient(patient.getId());
        assertThat(response.getCode()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("Patient with Id " + patient.getId() + "has been deleted successfully");
    }


    @Test
    public void when_updateStaff_should_return_success() throws IllegalAccessException {

        //Given
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Jonah");
        patient.setAge(22);
        patient.setLastVisitDate(LocalDate.now());

        // When
        when(patientRepository.save(patient)).thenReturn(patient);

        //Then
        ApiResponseDto response = patientService.updatePatient(patient);

        ArgumentCaptor<Patient> staffCapture = ArgumentCaptor.forClass(Patient.class);
        verify(patientRepository, Mockito.times(1)).save(staffCapture.capture());

        Patient expectedPatient = staffCapture.getValue();
        //Get field names from StaffResponseDto
        Field[] myFields = response.getData().getClass().getDeclaredFields();

        //Check if the saved name is Jonah
        Assertions.assertEquals("Jonah", myFields[1].get(response.getData()));
        assertThat(response.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getMessage()).isEqualTo("Patient records have been updated successfully");
    }


}
