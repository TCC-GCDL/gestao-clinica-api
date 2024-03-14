package com.tcc.gestaoclinica.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class MedicalCare {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Doctor doctor;

    private Patient patient;

    private String observation;

    private LocalDateTime date;

    private User user;

    private String status;

}
