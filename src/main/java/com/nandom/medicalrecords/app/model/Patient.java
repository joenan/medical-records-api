package com.nandom.medicalrecords.app.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name="patient")
public class Patient  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "name cannot be null or empty")
    @Column(name = "name", length = 100,updatable = false,nullable = false)
    private String name;

    @NotNull(message = "age cannot be null or empty")
    @Column(name = "age")
    private Integer age;

    @NotNull(message = "Staff UUID is required")
    @Column(name = "loggedin_staff_uuid")
    @Type(type = "uuid-char")
    private UUID loggedInstaffUuid;

    @Column(name="last_visit_date")
    private LocalDate lastVisitDate;

    public Patient() {
    }

    public Patient(String name, Integer age, LocalDate lastVisitDate) {
        this.name = name;
        this.age = age;
        this.lastVisitDate = lastVisitDate;
    }
}
