package com.tcc.gestaoclinica.domain.repositories;

import com.tcc.gestaoclinica.domain.models.Patient;
import com.tcc.gestaoclinica.domain.models.Status;
import com.tcc.gestaoclinica.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByCpf(String cpf);

    boolean existsByRenach(String renach);


}
