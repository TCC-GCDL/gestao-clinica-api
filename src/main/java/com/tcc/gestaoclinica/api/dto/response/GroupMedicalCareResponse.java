package com.tcc.gestaoclinica.api.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GroupMedicalCareResponse {

    private Long id;
    private String name;
    private String date;
    private UserGroupResponse user;
    private List<DoctorGroupResponse> doctors;
    private List<PatientGroupResponse> patients;

}
