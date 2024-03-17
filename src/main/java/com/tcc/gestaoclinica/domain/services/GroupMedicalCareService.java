package com.tcc.gestaoclinica.domain.services;

import com.tcc.gestaoclinica.domain.exceptions.CrmExistsException;
import com.tcc.gestaoclinica.domain.exceptions.EmailAlreadyExistsException;
import com.tcc.gestaoclinica.domain.exceptions.EntityNotFoundException;
import com.tcc.gestaoclinica.domain.exceptions.TelefoneExistsException;
import com.tcc.gestaoclinica.domain.models.Doctor;
import com.tcc.gestaoclinica.domain.models.GroupMedicalCare;
import com.tcc.gestaoclinica.domain.models.Patient;
import com.tcc.gestaoclinica.domain.repositories.DoctorRepository;
import com.tcc.gestaoclinica.domain.repositories.GroupMedialCareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupMedicalCareService {

    @Autowired
    private GroupMedialCareRepository groupMedicalCareRepository;

    @Autowired
    private PatientService patientService;

    public GroupMedicalCare save(GroupMedicalCare groupMedicalCare) {
        return groupMedicalCareRepository.save(groupMedicalCare);
    }

    public void addPatientToGroupMedicalCare(Long groupMedicalCareId, Long patientId) {
        GroupMedicalCare groupMedicalCare = searchOrFail(groupMedicalCareId);
        Patient patient = patientService.searchOrFail(patientId);
        if (groupMedicalCare.getPatients().contains(patient)) {
            throw new EntityNotFoundException("Paciente já cadastrado no grupo de atendimento médico");
        }
        groupMedicalCare.getPatients().add(patient);
        groupMedicalCareRepository.save(groupMedicalCare);
    }

    public GroupMedicalCare searchOrFail(Long id) {
        return groupMedicalCareRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Grupo de atendimento médico não encontrado"));
    }

}
