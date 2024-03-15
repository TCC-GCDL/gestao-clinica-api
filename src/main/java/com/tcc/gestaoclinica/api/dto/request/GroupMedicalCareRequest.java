package com.tcc.gestaoclinica.api.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupMedicalCareRequest {

    public String name;
    public Long userId;
    public Long doctorId;
    public LocalDateTime date;

}
