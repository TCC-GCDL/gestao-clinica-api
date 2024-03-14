package com.tcc.gestaoclinica.domain.services;

import com.tcc.gestaoclinica.domain.exceptions.*;
import com.tcc.gestaoclinica.domain.models.Doctor;
import com.tcc.gestaoclinica.domain.models.Patient;
import com.tcc.gestaoclinica.domain.repositories.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor createDoctor(Doctor doctor) {
        if(doctorRepository.existsByEmail(doctor.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        if(doctorRepository.existsByPhone(doctor.getPhone())) {
            throw new TelefoneExistsException();
        }

        if(doctorRepository.existsByCrm(doctor.getSpecialty())) {
            throw new CrmExistsException();
        }

        return doctorRepository.save(doctor);

    }

    public void updateDoctor(Long doctorId, Doctor doctor) {

        Doctor doctorToUpdate = searchOrFail(doctorId);

        // Verifica se o e-mail fornecido para atualização já está em uso por outro doctor
        if (!doctorToUpdate.getEmail().equals(doctor.getEmail()) && doctorRepository.existsByEmail(doctor.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // Verifica se o número de telefone fornecido para atualização já está em uso por outro doctor
        if (!doctorToUpdate.getPhone().equals(doctor.getPhone()) && doctorRepository.existsByPhone(doctor.getPhone())) {
            throw new TelefoneExistsException();
        }

        // Verifica se a especialidade fornecida para atualização já está em uso por outro doctor
        if (!doctorToUpdate.getSpecialty().equals(doctor.getSpecialty()) && doctorRepository.existsByCrm(doctor.getSpecialty())) {
            throw new CrmExistsException();
        }

        doctorToUpdate.setFirstName(doctor.getFirstName());
        doctorToUpdate.setLastName(doctor.getLastName());
        doctorToUpdate.setPhone(doctor.getPhone());
        doctorToUpdate.setCrm(doctor.getCrm());
        doctorToUpdate.setEmail(doctor.getEmail());
        doctorToUpdate.setSpecialty(doctor.getSpecialty());

        doctorRepository.save(doctorToUpdate);
    }

    public Doctor searchOrFail(Long doctorId) {
        return doctorRepository.findById(doctorId).orElseThrow(() -> new EntityNotFoundException("Médico nao encontrado"));
    }

}
