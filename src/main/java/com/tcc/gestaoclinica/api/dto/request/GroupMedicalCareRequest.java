package com.tcc.gestaoclinica.api.dto.request;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class GroupMedicalCareRequest {

    private String name;

    private Long userId;

    private Long doctorId;

    private Long patientId;

    private LocalDateTime date;

}
