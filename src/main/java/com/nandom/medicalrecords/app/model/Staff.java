package com.nandom.medicalrecords.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name="staff")
public class Staff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false,updatable = false)
    @Type(type = "uuid-char")
    private UUID staffUuid;

    @NotNull(message = "name cannot be null or empty")
    @Column(name = "name", length = 100,updatable = false,nullable = false)
    private String name;

    @Column(name="registration_date")
    private LocalDate registrationDate;

    public Staff(UUID staffUuid, String name, LocalDate registrationDate) {
        this.staffUuid = staffUuid;
        this.name = name;
        this.registrationDate = registrationDate;
    }
}
