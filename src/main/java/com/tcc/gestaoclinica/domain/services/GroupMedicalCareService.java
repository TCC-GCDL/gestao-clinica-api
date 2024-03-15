package com.tcc.gestaoclinica.domain.services;

import com.tcc.gestaoclinica.domain.exceptions.CrmExistsException;
import com.tcc.gestaoclinica.domain.exceptions.EmailAlreadyExistsException;
import com.tcc.gestaoclinica.domain.exceptions.EntityNotFoundException;
import com.tcc.gestaoclinica.domain.exceptions.TelefoneExistsException;
import com.tcc.gestaoclinica.domain.models.Doctor;
import com.tcc.gestaoclinica.domain.models.GroupMedicalCare;
import com.tcc.gestaoclinica.domain.repositories.DoctorRepository;
import com.tcc.gestaoclinica.domain.repositories.GroupMedialCareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupMedicalCareService {

    @Autowired
    private GroupMedialCareRepository groupMedicalCareRepository;

    public GroupMedicalCare save(GroupMedicalCare groupMedicalCare) {
        return groupMedicalCareRepository.save(groupMedicalCare);
    }

    public GroupMedicalCare searchOrFail(Long id) {
        return groupMedicalCareRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Grupo de atendimento médico não encontrado"));
    }

}
