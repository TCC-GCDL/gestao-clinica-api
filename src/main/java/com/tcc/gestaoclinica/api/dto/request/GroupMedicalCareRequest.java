package com.tcc.gestaoclinica.api.dto.request;

import com.tcc.gestaoclinica.domain.models.Shift;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GroupMedicalCareRequest {

    public String name;
    public Long userId;
    public Long doctorId;
    public LocalDate date;
    private Shift shift;

}
