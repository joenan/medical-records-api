package com.nandom.medicalrecords.app.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class StaffRequestDto implements Serializable {

    @ApiModelProperty(notes = "Staff Id", name="id", required=false)
    private Long id;

    @ApiModelProperty(example = "name", required = true, dataType = "String", notes = "Full name of Staff")
    @NotNull(message = "Staff name is required")
    private String name;

    @ApiModelProperty(example = "UUID", required = true, dataType = "UUID", notes = "Logged in UUID of staff")
    @NotNull(message = "Logged in Staff UUID is required")
    private String loggedInstaffUuid;

}
