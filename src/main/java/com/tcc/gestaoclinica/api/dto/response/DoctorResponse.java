package com.tcc.gestaoclinica.api.dto.response;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class DoctorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String crm;
    private String email;
    private String specialty;
}
