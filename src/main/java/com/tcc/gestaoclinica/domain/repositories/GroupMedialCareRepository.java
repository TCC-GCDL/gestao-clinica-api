package com.tcc.gestaoclinica.domain.repositories;

import com.tcc.gestaoclinica.domain.models.Doctor;
import com.tcc.gestaoclinica.domain.models.GroupMedicalCare;
import com.tcc.gestaoclinica.domain.models.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupMedialCareRepository extends JpaRepository<GroupMedicalCare, Long> {
    Page<GroupMedicalCare> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
