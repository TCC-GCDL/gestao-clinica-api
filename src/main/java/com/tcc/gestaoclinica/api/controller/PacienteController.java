package com.tcc.gestaoclinica.api.controller;

import com.tcc.gestaoclinica.api.dto.PacienteDto;
import com.tcc.gestaoclinica.domain.models.Paciente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/patient")
@RestController
public class PacienteController {


    @PostMapping
    public ResponseEntity<PacienteDto> createPaciente() {
        return null;
    }


}
