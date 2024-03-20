package com.tcc.gestaoclinica.domain.models;

import jakarta.persistence.Entity;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Patient extends People {

    private String renach;
    private String email;
    private CategoryCNH categoryCNH;
    private MaritalStatus maritalStatus;
    private String rg;


}
