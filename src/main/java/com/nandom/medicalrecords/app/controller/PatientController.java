package com.nandom.medicalrecords.app.controller;


import com.nandom.medicalrecords.app.model.Patient;
import com.nandom.medicalrecords.app.payload.request.PatientDateRequestDto;
import com.nandom.medicalrecords.app.payload.request.PatientRequestDto;
import com.nandom.medicalrecords.app.payload.response.ApiResponseDto;
import com.nandom.medicalrecords.app.service.PatientService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/patient")
@Api(value = "Patients", description = "Operations pertaining to Patients on Medical Records API")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @ApiOperation(value = "Add new Patient", response = ApiResponseDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @ApiResponses(value = {
            @ApiResponse(code = 203, message = "Created |OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")}
    )

    @PreAuthorize("hasRole('STAFF') and principal.staffUuid == T(java.util.UUID).fromString(#data.loggedInstaffUuid)")
    @PostMapping("/profile")
    public ResponseEntity<ApiResponseDto> createPatient(@RequestBody @Valid PatientRequestDto data) {
        Patient target = new Patient();
        BeanUtils.copyProperties(data, target);
        System.out.println(target);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.addPatient(target));
    }


    @ApiOperation(value = "Get list of all Patients in the System ", response = ApiResponseDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")}
    )
    @PreAuthorize("hasRole('STAFF') and principal.staffUuid == T(java.util.UUID).fromString(#loggedInstaffUuid)")
    @GetMapping("/profiles")
    public ResponseEntity<ApiResponseDto> getAllPatients(
            @RequestParam(value = "loggedInstaffUuid", required = true) String loggedInstaffUuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {
        Pageable paging = PageRequest.of(page, size);

        return ResponseEntity.ok().body(patientService.findAllPatients(paging));
    }

    @ApiOperation(value = "Download Patient Record in CSV Format", response = ApiResponseDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")}
    )
    @PreAuthorize("hasRole('STAFF') and principal.staffUuid == T(java.util.UUID).fromString(#loggedInstaffUuid)")
    @GetMapping("/profile/csv/download/{patientId}")
    public ResponseEntity downloadPatientProfile(@RequestParam(value = "loggedInstaffUuid", required = true) String loggedInstaffUuid, @PathVariable("patientId")  Long patientId) {
        final InputStreamResource resource = new InputStreamResource(patientService.downloadPatientProfile(patientId));
        String fileName = "PatientProfile"+patientId+".csv";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, fileName)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(resource);
    }

    @ApiOperation(value = "Fetch all patients from Zero years to the specified years", response = ApiResponseDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")}
    )

    @PreAuthorize("hasRole('STAFF') and principal.staffUuid == T(java.util.UUID).fromString(#loggedInstaffUuid)")
    @GetMapping("/profiles/upto/{years}/")
    public ResponseEntity<ApiResponseDto> fetchProfilesUptoStatedYears(@RequestParam(value = "loggedInstaffUuid", required = true) String loggedInstaffUuid, @PathVariable("years") Integer years) {
        return ResponseEntity.ok().body(patientService.findAllPatientsByAgeUpto(years));
    }

    @ApiOperation(value = "Delete all Patients within the specified Date Range", response = ApiResponseDto.class)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")}
    )
    @PreAuthorize("hasRole('STAFF') and principal.staffUuid == T(java.util.UUID).fromString(#data.loggedInstaffUuid)")
    @DeleteMapping("/profiles/delete")
    public ResponseEntity<ApiResponseDto> deletePatientProfileByDateRange(@Valid @RequestBody PatientDateRequestDto data) {
        return ResponseEntity.ok().body(patientService.deleteMultiplePatientsLastVisitDateBetween(data.getDateFrom(), data.getDateTo()));
    }

}
