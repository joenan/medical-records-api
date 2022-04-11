package com.nandom.medicalrecords.app.repository;

import com.nandom.medicalrecords.app.model.ERole;
import com.nandom.medicalrecords.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
