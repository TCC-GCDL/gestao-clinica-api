package com.tcc.gestaoclinica.api.dto.response;

import com.tcc.gestaoclinica.domain.models.CategoryCNH;
import com.tcc.gestaoclinica.domain.models.Gender;
import com.tcc.gestaoclinica.domain.models.MaritalStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponse {

    @NotBlank
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String phone;

    @NotBlank
    private String city;

    @NotBlank
    private String cpf;

    @NotBlank
    private Gender gender;

    private LocalDate dateOfBirth;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    private String complement;

    @NotBlank
    private String neighborhood;

    @NotBlank
    private String state;

    @NotBlank
    private String email;

    @NotBlank
    private String renach;

    @NotBlank
    private CategoryCNH categoryCNH;

    @NotBlank
    private MaritalStatus maritalStatus;

    @NotBlank
    private String rg;


}
