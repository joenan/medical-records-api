package com.nandom.medicalrecords.app.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
public class PatientRequestDto implements Serializable {

    @ApiModelProperty(example = "name", required = true, dataType = "String", notes = "Full name of Patient")
    @NotNull(message = "Patient name is required")
    private String name;

    @ApiModelProperty(example = "age", required = true, dataType = "Integer", notes = "Age of Patient")
    @NotNull(message = "Patient age is required")
    private Integer age;

    @ApiModelProperty(example = "UUID", required = true, dataType = "UUID", notes = "Logged in UUID of staff")
    @NotNull(message = "Logged in Staff UUID is required")
    private UUID loggedInstaffUuid;
}
