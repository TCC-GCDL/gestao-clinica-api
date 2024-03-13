package com.tcc.gestaoclinica.api.dto.request;

import com.tcc.gestaoclinica.domain.models.Gender;
import com.tcc.gestaoclinica.domain.models.RoleName;
import com.tcc.gestaoclinica.domain.models.Status;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateUserDto(
        @NotBlank String firstName, String lastName, String phone, String city, String cpf,
        Gender gender, LocalDate dateOfBirth, String zipCode, String street,
        String number, String complement, String neighborhood, String state,
        @NotBlank String email, String password, RoleName role, Status status) {
}