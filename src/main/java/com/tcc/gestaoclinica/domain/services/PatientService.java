package com.tcc.gestaoclinica.domain.services;

import com.tcc.gestaoclinica.domain.exceptions.*;
import com.tcc.gestaoclinica.domain.models.Patient;
import com.tcc.gestaoclinica.domain.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient save(Patient patient) {

        if(patientRepository.existsByEmail(patient.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        if(patientRepository.existsByPhone(patient.getPhone())) {
            throw new TelefoneExistsException();
        }

        if(patientRepository.existsByCpf(patient.getCpf())) {
            throw new CpfExistsException();
        }

        if(patientRepository.existsByRenach(patient.getRenach())) {
            throw new RenachExistsException();
        }

        Patient patient1 = patientRepository.save(patient);

        return patient1;

    }

    public Patient searchOrFail(Long cidadeId) {
        return patientRepository.findById(cidadeId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente nao encontrado"));
    }

}
