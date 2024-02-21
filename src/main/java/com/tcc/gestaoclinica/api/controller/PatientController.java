package com.tcc.gestaoclinica.api.controller;

import com.tcc.gestaoclinica.api.assembler.PatientInputDisasembler;
import com.tcc.gestaoclinica.api.dto.request.PatientRequest;
import com.tcc.gestaoclinica.api.dto.response.PatientResponse;
import com.tcc.gestaoclinica.domain.models.Address;
import com.tcc.gestaoclinica.domain.models.Patient;
import com.tcc.gestaoclinica.domain.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/patient")
@RestController
public class PatientController {

    @Autowired
    private PatientInputDisasembler patientInputDisasembler;

    @Autowired
    private PatientService patientService;

    @PostMapping
    public PatientResponse createPatient(@RequestBody PatientRequest patientRequest) {

        Patient patient = reqToModel(patientRequest);

        return patientToResponse(patientService.save(patient));
    }

    public Patient reqToModel(PatientRequest patientRequest) {

        Patient patient = new Patient();
        patient.setFirstName(patientRequest.getFirstName());
        patient.setLastName(patientRequest.getLastName());
        patient.setEmail(patientRequest.getEmail());
        patient.setCategoryCNH(patientRequest.getCategoryCNH());
        patient.setRg(patientRequest.getRg());
        patient.setMaritalStatus(patientRequest.getMaritalStatus());
        patient.setCpf(patientRequest.getCpf());
        patient.setRenach(patientRequest.getRenach());
        patient.setDateOfBirth(patientRequest.getDateOfBirth());
        patient.setGender(patientRequest.getGender());
        patient.setPhone(patientRequest.getPhone());

        Address address = new Address();
        address.setNumber(patientRequest.getNumber());
        address.setStreet(patientRequest.getStreet());
        address.setState(patientRequest.getState());
        address.setComplement(patientRequest.getComplement());
        address.setNeighborhood(patientRequest.getNeighborhood());
        address.setCity(patientRequest.getCity());
        address.setZipCode(patientRequest.getZipCode());
        address.setId(patient.getId());

        patient.setAddress(address);

        return patient;
    }

    public PatientResponse patientToResponse(Patient patient) {
        PatientResponse response = new PatientResponse();

        response.setId(patient.getId());
        response.setFirstName(patient.getFirstName());
        response.setLastName(patient.getLastName());
        response.setPhone(patient.getPhone());
        response.setCity(patient.getAddress().getCity());
        response.setCpf(patient.getCpf());
        response.setGender(patient.getGender());
        response.setDateOfBirth(patient.getDateOfBirth());
        response.setZipCode(patient.getAddress().getZipCode());
        response.setStreet(patient.getAddress().getStreet());
        response.setNumber(patient.getAddress().getNumber());
        response.setComplement(patient.getAddress().getComplement());
        response.setNeighborhood(patient.getAddress().getNeighborhood());
        response.setState(patient.getAddress().getState());
        response.setEmail(patient.getEmail());
        response.setRenach(patient.getRenach());
        response.setCategoryCNH(patient.getCategoryCNH());
        response.setMaritalStatus(patient.getMaritalStatus());

        return response;
    }


}
