package com.tcc.gestaoclinica.api.dto;

import com.tcc.gestaoclinica.domain.models.RoleName;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreatePacienteDto(
        @NotBlank String firstName, String lastName, String phone, String city, String cpf,
        String gender, LocalDate dateOfBirth, String zipCode, String street,
        String number, String complement, String neighborhood, String state
) {
}
