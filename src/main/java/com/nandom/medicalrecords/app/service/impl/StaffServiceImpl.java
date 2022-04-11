package com.nandom.medicalrecords.app.service.impl;

import com.nandom.medicalrecords.app.model.Staff;
import com.nandom.medicalrecords.app.payload.response.ApiResponseDto;
import com.nandom.medicalrecords.app.payload.response.StaffResponseDto;
import com.nandom.medicalrecords.app.repository.StaffRepository;
import com.nandom.medicalrecords.app.service.StaffService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StaffServiceImpl implements StaffService {

    private StaffRepository staffRepository;

    public StaffServiceImpl(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public ApiResponseDto addStaff(Staff staff) {

        if (staff.getId() == null || staff.getId() == 0) {
            staff.setStaffUuid(UUID.randomUUID());
            staff.setRegistrationDate(LocalDate.now());
        }

        Staff s = staffRepository.save(staff);
        ApiResponseDto response = new ApiResponseDto();

        StaffResponseDto responseDto = new StaffResponseDto();
        BeanUtils.copyProperties(s, responseDto);

        response.setCode(HttpStatus.CREATED.value());
        response.setData(responseDto);
        response.setMessage("Staff record saved successfully");
        return response;

    }

    @Override
    @Transactional
    public ApiResponseDto deleteStaffByUUID(UUID uuid) {

        Optional<Staff> staff = staffRepository.findStaffByStaffUuid(uuid);
        if (staff.isPresent()) {
            ApiResponseDto response = new ApiResponseDto();
            staffRepository.deleteStaffByStaffUuid(staff.get().getStaffUuid());

            response.setMessage("Staff with uuid " + uuid + " has been deleted successfully");
            response.setCode(HttpStatus.OK.value());
            response.setData(response);
            return response;
        }
        ApiResponseDto response = new ApiResponseDto();
        response.setMessage("Staff uuid " + uuid + " is not found");
        response.setCode(HttpStatus.NOT_FOUND.value());
        return response;

    }

    @Override
    @Transactional
    public ApiResponseDto updateStaff(Staff staff) {

        ApiResponseDto response = new ApiResponseDto();
        Optional<Staff> searchedStaff = staffRepository.findById(staff.getId());

        if (searchedStaff.isPresent()) {
            searchedStaff.get().setName(staff.getName());
            System.out.println("---------------------------------------------------------------------------");
            System.out.println(searchedStaff.get());
            System.out.println("---------------------------------------------------------------------------");

            Staff savedStaff = staffRepository.save(searchedStaff.get());

            StaffResponseDto responseDto = new StaffResponseDto();
            BeanUtils.copyProperties(savedStaff, responseDto);
            response.setMessage("Staff records have been updated successfully");
            response.setCode(HttpStatus.OK.value());
            response.setData(responseDto);

            return response;
        }
        response.setMessage("Staff records not found");
        response.setCode(HttpStatus.NOT_FOUND.value());
        return response;
    }

    @Override
    public ApiResponseDto findStaffByUUID(UUID uuid) {

        Optional<Staff> staff = staffRepository.findStaffByStaffUuid(uuid);
        if (staff.isPresent()) {
            ApiResponseDto response = new ApiResponseDto();
            staffRepository.deleteStaffByStaffUuid(staff.get().getStaffUuid());

            response.setMessage("Staff data with uuid " + uuid + "has been found");
            response.setCode(HttpStatus.OK.value());
            response.setData(response);
            return response;
        }
        ApiResponseDto response = new ApiResponseDto();
        response.setMessage("Staff with uuid " + uuid + " is not found");
        response.setCode(HttpStatus.NOT_FOUND.value());
        return response;

    }

    @Override
    public ApiResponseDto findAllStaff() {

        List<Staff> staffList = staffRepository.findAll();
        List<StaffResponseDto> staffResponseDtoList = new ArrayList<>();
        for (Staff source : staffList) {
            StaffResponseDto target = new StaffResponseDto();
            BeanUtils.copyProperties(source, target);
            staffResponseDtoList.add(target);
        }

        ApiResponseDto response = new ApiResponseDto();
        response.setMessage("List of all Staff");
        response.setCode(HttpStatus.OK.value());
        response.setData(staffResponseDtoList);
        return response;

    }
}
