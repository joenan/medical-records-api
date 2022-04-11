package com.nandom.medicalrecords.app.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class PatientDateRequestDto implements Serializable {

    @ApiModelProperty(example = "dateFrom", required = true, dataType = "String", notes = "dateFrom")
    @NotNull(message = "dateFrom id is required")
    private LocalDate dateFrom;

    @ApiModelProperty(example = "dateTo", required = true, dataType = "String", notes = "dateTo")
    @NotNull(message = "dateTo id is required")
    private LocalDate dateTo;

    @ApiModelProperty(example = "UUID", required = true, dataType = "UUID", notes = "Logged in UUID of staff")
    @NotNull(message = "Logged in Staff UUID is required")
    private UUID loggedInstaffUuid;

}
