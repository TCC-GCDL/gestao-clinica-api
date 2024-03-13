package com.tcc.gestaoclinica.api.dto.request;

import com.tcc.gestaoclinica.domain.models.RoleName;
import com.tcc.gestaoclinica.domain.models.Status;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record TokenDto(
        @NotBlank String token) {
}