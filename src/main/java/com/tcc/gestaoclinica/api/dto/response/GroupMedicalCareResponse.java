package com.tcc.gestaoclinica.api.dto.response;

import com.tcc.gestaoclinica.domain.models.Shift;
import lombok.Data;

import java.util.List;

@Data
public class GroupMedicalCareResponse {

    private Long id;
    private String name;
    private String date;
    private UserGroupResponse user;
    private DoctorGroupResponse doctor;
    private List<PatientGroupResponse> patients;
    private Shift shift;

}
