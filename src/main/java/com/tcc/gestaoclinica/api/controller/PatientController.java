package com.tcc.gestaoclinica.api.controller;

import com.tcc.gestaoclinica.api.dto.PacienteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/patient")
@RestController
public class PatientController {


    @PostMapping
    public ResponseEntity<PacienteDto> createPaciente() {
        return null;
    }


}
