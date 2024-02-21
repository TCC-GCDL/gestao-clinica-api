package com.tcc.gestaoclinica.api.dto;

import com.tcc.gestaoclinica.domain.models.RoleName;
import com.tcc.gestaoclinica.domain.models.Status;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateUserDto(
        @NotBlank String nome, String sobrenome, String telefone, String cidade, String cpf,
        String genero, LocalDate dataDeNascimento, String cep, String rua,
        String numero, String complemento, String bairro, String estado,
        @NotBlank String email, String password, RoleName role, Status status) {
}