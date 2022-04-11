package com.nandom.medicalrecords.app.services.staff;


import com.nandom.medicalrecords.app.model.Staff;
import com.nandom.medicalrecords.app.payload.request.StaffRequestDto;
import com.nandom.medicalrecords.app.payload.response.ApiResponseDto;
import com.nandom.medicalrecords.app.repository.StaffRepository;
import com.nandom.medicalrecords.app.service.StaffService;
import com.nandom.medicalrecords.app.service.impl.StaffServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffUnitTest {

    @Mock
    private StaffRepository staffRepository;

    StaffService staffService;

    @BeforeEach
    void initUseCase() {
        staffService = new StaffServiceImpl(staffRepository);
    }



    @Test
    public void savedStaff_Success() throws NoSuchFieldException, IllegalAccessException {
       //Given
        //receiving request as dto and converting to entity
        StaffRequestDto dto = new StaffRequestDto();
        dto.setName("Jonathan Princewill");

        Staff staff = new Staff();

        BeanUtils.copyProperties(dto, staff);
        staff.setStaffUuid(UUID.randomUUID());
        staff.setRegistrationDate(LocalDate.now());

        // When
        when(staffRepository.save(staff)).thenReturn(staff);

        //Then
        ApiResponseDto response = staffService.addStaff(staff);

        ArgumentCaptor<Staff> staffCapture = ArgumentCaptor.forClass(Staff.class);
        verify(staffRepository, Mockito.times(1)).save(staffCapture.capture());

        Staff expectedStaff = staffCapture.getValue();

        //Get field names from StaffResponseDto
        Field[] myFields = response.getData().getClass().getDeclaredFields();

        Assertions.assertEquals(expectedStaff.getName(), myFields[1].get(response.getData()));
        Assertions.assertEquals(expectedStaff.getStaffUuid(), myFields[2].get(response.getData()));
        assertThat(response.getCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getMessage()).isEqualTo("Staff record saved successfully");
    }

    @Test
    public void getAllStaffListTest() throws IllegalAccessException {
        List<Staff> list = new ArrayList<Staff>();
        Staff staffOne = new Staff(UUID.randomUUID(), "Jonathan Princewill", LocalDate.now());
        Staff StaffTwo = new Staff(UUID.randomUUID(), "Josepeh Marwa", LocalDate.now());
        Staff staffThree = new Staff(UUID.randomUUID(), "Stephen Ashley", LocalDate.now());

        list.add(staffOne);
        list.add(StaffTwo);
        list.add(staffThree);

        //When
        when(staffRepository.findAll()).thenReturn(list);

        //then
        ApiResponseDto response = staffService.findAllStaff();

        //Get field names from StaffResponseDto
        Field[] myFields = response.getData().getClass().getDeclaredFields();

        assertThat(response.getCode()).isEqualTo(200);
        assertThat(response.getMessage()).isEqualTo("List of all Staff");
    }


    @Test
    public void when_deleteStaff_should_return_success()  {
        //Given
        UUID uuid = UUID.randomUUID();

        Staff staff = new Staff();
        staff.setId(1L);
        staff.setName("Princewill Emmanuel");
        staff.setRegistrationDate(LocalDate.now());
        staff.setStaffUuid(uuid);

        when(staffRepository.findStaffByStaffUuid(uuid)).thenReturn(Optional.of(staff));

        // instruct mock for "second interaction" (see testee code):
        doNothing().when(staffRepository).deleteStaffByStaffUuid(uuid); // we could also use ...delete(any(Long.class))  or ..anyLong();
        // When: (do it!)
       ApiResponseDto response = staffService.deleteStaffByUUID(uuid);
       assertThat(response.getCode()).isEqualTo(200);
       assertThat(response.getMessage()).isEqualTo("Staff with uuid " + uuid + " has been deleted successfully");
    }


    @Test
    public void when_updateStaff_should_return_success() throws IllegalAccessException {

        //Given
        UUID uuid = UUID.randomUUID();
        Long StaffId = 1L;
        Staff staff = new Staff();
        staff.setId(StaffId);
        staff.setName("Jonah");
        staff.setRegistrationDate(LocalDate.now());
        staff.setStaffUuid(uuid);

        // When
        when(staffRepository.save(staff)).thenReturn(staff);
        when(staffRepository.findById(StaffId)).thenReturn(Optional.of(staff));

        //Then
        ApiResponseDto response = staffService.updateStaff(staff);

        ArgumentCaptor<Staff> staffCapture = ArgumentCaptor.forClass(Staff.class);
        verify(staffRepository).save(staffCapture.capture());

        Staff expectedStaff = staffCapture.getValue();
        //Get field names from StaffResponseDto
        Field[] myFields = response.getData().getClass().getDeclaredFields();

        //Check if the saved name is Jonah
        Assertions.assertEquals("Jonah", myFields[1].get(response.getData()));
        assertThat(response.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getMessage()).isEqualTo("Staff records have been updated successfully");
    }


}
