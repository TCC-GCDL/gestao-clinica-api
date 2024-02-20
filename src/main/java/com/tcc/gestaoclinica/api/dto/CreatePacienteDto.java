package com.tcc.gestaoclinica.api.dto;

import com.tcc.gestaoclinica.domain.models.RoleName;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreatePacienteDto(
        @NotBlank String nome, String sobrenome, String telefone, String cidade, String cpf,
        String genero, LocalDate dataDeNascimento, String cep, String rua,
        String numero, String complemento, String bairro, String estado
) {
}
