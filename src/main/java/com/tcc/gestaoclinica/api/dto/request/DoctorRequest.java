package com.tcc.gestaoclinica.api.dto.request;

import lombok.Data;

@Data
public class DoctorRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private String crm;
    private String email;
    private String specialty;
}
