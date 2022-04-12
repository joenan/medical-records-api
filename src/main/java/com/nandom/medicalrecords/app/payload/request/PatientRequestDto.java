package com.nandom.medicalrecords.app.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
public class PatientRequestDto implements Serializable {

    @ApiModelProperty(position = 0 ,notes = "Staff Id", name="id", required=false)
    private Long id;

    @ApiModelProperty(position = 1, example = "name", required = true, dataType = "String", notes = "Full name of Patient")
    @NotNull(message = "Patient name is required")
    private String name;

    @ApiModelProperty(position = 2, notes = "age", name="age", required = true)
    @NotNull(message = "Patient age is required")
    private Integer age;

    @ApiModelProperty(position = 3, example = "UUID", required = true, dataType = "UUID", notes = "Logged in UUID of staff")
    @NotNull(message = "Logged in Staff UUID is required")
    private UUID loggedInstaffUuid;
}
