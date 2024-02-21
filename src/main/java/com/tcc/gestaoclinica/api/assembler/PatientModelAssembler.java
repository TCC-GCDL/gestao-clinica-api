package com.tcc.gestaoclinica.api.assembler;

import com.tcc.gestaoclinica.api.dto.response.PatientResponse;
import com.tcc.gestaoclinica.domain.models.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PatientResponse toModel(Patient patient) {
        return modelMapper.map(patient, PatientResponse.class);
    }

    public List<PatientResponse> toCollectionModel(List<Patient> patients) {
        return patients.stream()
                .map(patient -> toModel(patient))
                .collect(Collectors.toList());
    }

}
