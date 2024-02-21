package com.tcc.gestaoclinica.domain.services;

import com.tcc.gestaoclinica.domain.exceptions.DefaultException;
import com.tcc.gestaoclinica.domain.exceptions.EntityNotFoundException;
import com.tcc.gestaoclinica.domain.models.Patient;
import com.tcc.gestaoclinica.domain.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient save(Patient patient) {

        Patient patient1 = patientRepository.save(patient);

        return patient1;

    }

    public Patient searchOrFail(Long cidadeId) {
        return patientRepository.findById(cidadeId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente nao encontrado"));
    }



}
