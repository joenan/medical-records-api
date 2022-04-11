package com.nandom.medicalrecords.app.payload.response;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PatientResponseDto implements Serializable {
    public Long id;
    public String name;
    public Integer age;
    public LocalDate lastVisitDate;
}
