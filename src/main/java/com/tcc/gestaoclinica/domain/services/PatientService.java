package com.tcc.gestaoclinica.domain.services;

import com.tcc.gestaoclinica.domain.exceptions.*;
import com.tcc.gestaoclinica.domain.models.Address;
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

    public Patient update(Patient updatedPatient) {
        // Verifica se o paciente que está sendo atualizado existe no banco de dados
        Patient existingPatient = patientRepository.findById(updatedPatient.getId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        // Verifica se o e-mail fornecido para atualização já está em uso por outro paciente
        if (!existingPatient.getEmail().equals(updatedPatient.getEmail()) && patientRepository.existsByEmail(updatedPatient.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // Verifica se o número de telefone fornecido para atualização já está em uso por outro paciente
        if (!existingPatient.getPhone().equals(updatedPatient.getPhone()) && patientRepository.existsByPhone(updatedPatient.getPhone())) {
            throw new TelefoneExistsException();
        }

        // Verifica se o CPF fornecido para atualização já está em uso por outro paciente
        if (!existingPatient.getCpf().equals(updatedPatient.getCpf()) && patientRepository.existsByCpf(updatedPatient.getCpf())) {
            throw new CpfExistsException();
        }

        // Verifica se o Renach fornecido para atualização já está em uso por outro paciente
        if (!existingPatient.getRenach().equals(updatedPatient.getRenach()) && patientRepository.existsByRenach(updatedPatient.getRenach())) {
            throw new RenachExistsException();
        }

        // Atualiza as informações do paciente existente com as informações do paciente atualizado
        existingPatient.setFirstName(updatedPatient.getFirstName());
        existingPatient.setLastName(updatedPatient.getLastName());
        existingPatient.setEmail(updatedPatient.getEmail());
        existingPatient.setPhone(updatedPatient.getPhone());
        existingPatient.setCpf(updatedPatient.getCpf());
        existingPatient.setRenach(updatedPatient.getRenach());
        existingPatient.setAddress(updatedPatient.getAddress());
        existingPatient.setGender(updatedPatient.getGender());
        existingPatient.setDateOfBirth(updatedPatient.getDateOfBirth());
        existingPatient.setCategoryCNH(updatedPatient.getCategoryCNH());
        existingPatient.setMaritalStatus(updatedPatient.getMaritalStatus());
        existingPatient.setRg(updatedPatient.getRg());

        // Salva e retorna o paciente atualizado
        return patientRepository.save(existingPatient);
    }

    public Patient searchOrFail(Long cidadeId) {
        return patientRepository.findById(cidadeId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente nao encontrado"));
    }

}
