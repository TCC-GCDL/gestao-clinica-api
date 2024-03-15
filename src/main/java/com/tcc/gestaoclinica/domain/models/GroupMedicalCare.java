package com.tcc.gestaoclinica.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class GroupMedicalCare  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "group_medical_care_patients",
            joinColumns = @JoinColumn(name = "group_medical_care_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patients = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "group_medical_care_doctors",
            joinColumns = @JoinColumn(name = "group_medical_care_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id"))
    private List<Doctor> doctors = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    private LocalDateTime date;



}
