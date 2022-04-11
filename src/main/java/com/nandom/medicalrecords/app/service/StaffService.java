package com.nandom.medicalrecords.app.service;

import com.nandom.medicalrecords.app.model.Staff;
import com.nandom.medicalrecords.app.payload.response.ApiResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StaffService {
    ApiResponseDto addStaff(Staff staff);
    ApiResponseDto deleteStaffByUUID(UUID uuid);
    ApiResponseDto updateStaff(Staff staff);
    ApiResponseDto findStaffByUUID(UUID uuid);
    ApiResponseDto findAllStaff(Pageable page);
}
