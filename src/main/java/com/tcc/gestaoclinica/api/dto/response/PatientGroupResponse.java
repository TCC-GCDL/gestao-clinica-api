package com.tcc.gestaoclinica.api.dto.response;

import lombok.Data;

@Data
public class PatientGroupResponse {

    private Long id;
    private String name;
    private String phone;
    private String email;
}
