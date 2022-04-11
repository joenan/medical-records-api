package com.nandom.medicalrecords.app.controller;

import com.nandom.medicalrecords.app.model.Staff;
import com.nandom.medicalrecords.app.payload.request.StaffRequestDto;
import com.nandom.medicalrecords.app.payload.response.ApiResponseDto;
import com.nandom.medicalrecords.app.service.StaffService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

    private StaffService staffService;


    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @ApiOperation(value = "Create a new staff Profile", response = ApiResponseDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @ApiResponses(value = {
            @ApiResponse(code = 203, message = "Created"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")}
    )

    @PreAuthorize("hasRole('STAFF') and principal.staffUuid == T(java.util.UUID).fromString(#data.loggedInstaffUuid)")
    @PostMapping("/profile/")
    public ResponseEntity<ApiResponseDto> saveStaff(@Valid @RequestBody StaffRequestDto data) {
        Staff staff = new Staff();
        BeanUtils.copyProperties(data, staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(staffService.addStaff(staff));
    }

    @ApiOperation(value = "Update Staff Profile", response = ApiResponseDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")}
    )
    @PreAuthorize("hasRole('STAFF') and principal.staffUuid == T(java.util.UUID).fromString(#data.loggedInstaffUuid)")
    @PutMapping("/profile")
    public ResponseEntity<ApiResponseDto> updateStaff(@Valid @RequestBody StaffRequestDto data) {
        Staff staff = new Staff();
        BeanUtils.copyProperties(data, staff);
        return ResponseEntity.ok().body(staffService.updateStaff(staff));
    }


    @ApiOperation(value = "Find all Staff", response = ApiResponseDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")}
    )
    @PreAuthorize("hasRole('STAFF') and principal.staffUuid == T(java.util.UUID).fromString(#loggedInstaffUuid)")
    @GetMapping("/profiles")
    public ResponseEntity<ApiResponseDto> findAllStaff(@RequestParam(value = "loggedInstaffUuid", required = true) String loggedInstaffUuid) {
        return ResponseEntity.ok().body(staffService.findAllStaff());
    }



}
