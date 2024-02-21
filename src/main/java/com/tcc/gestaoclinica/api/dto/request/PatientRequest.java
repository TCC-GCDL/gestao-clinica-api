package com.tcc.gestaoclinica.api.dto.request;

import com.tcc.gestaoclinica.domain.models.CategoryCNH;
import com.tcc.gestaoclinica.domain.models.MaritalStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequest {

    @NotBlank
    private String firstName;
    private String email;
    private String lastName;
    private String phone;
    private String city;
    private String cpf;
    private String gender;
    private LocalDate dateOfBirth;
    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String neighborhood;
    private String state;
    private String rg;
    private CategoryCNH categoryCNH;
    private MaritalStatus maritalStatus;
    private String renach;


}
