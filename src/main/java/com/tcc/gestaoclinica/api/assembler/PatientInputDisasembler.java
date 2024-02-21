package com.tcc.gestaoclinica.api.assembler;

import com.tcc.gestaoclinica.api.dto.request.PatientRequest;
import com.tcc.gestaoclinica.domain.models.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientInputDisasembler {

    @Autowired
    private ModelMapper modelMapper;

    public Patient toDomainObject(PatientRequest patientRequest) {
        return modelMapper.map(patientRequest, Patient.class);
    }

    public void copyToDomainObject(PatientRequest pedidoInput, Patient pedido) {
        modelMapper.map(pedidoInput, pedido);
    }

}
