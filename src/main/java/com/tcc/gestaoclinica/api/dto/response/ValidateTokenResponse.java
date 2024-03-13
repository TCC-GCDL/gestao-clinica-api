package com.tcc.gestaoclinica.api.dto.response;

import com.tcc.gestaoclinica.domain.models.CategoryCNH;
import com.tcc.gestaoclinica.domain.models.MaritalStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ValidateTokenResponse {

    @NotBlank
    private String message;

    @NotBlank
    private Boolean isValid;
}
