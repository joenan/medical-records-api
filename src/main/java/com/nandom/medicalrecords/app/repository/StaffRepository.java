package com.nandom.medicalrecords.app.repository;

import com.nandom.medicalrecords.app.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findStaffByStaffUuid(UUID uuid);

    @Modifying
    void deleteStaffByStaffUuid(UUID uuid);
}
