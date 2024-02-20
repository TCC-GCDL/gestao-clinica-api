package com.tcc.gestaoclinica.domain.models;

import jakarta.persistence.Entity;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Paciente extends Pessoa {

    private String renach;
    private String email;
    private CategoriaCNH categoriaCNH;
    private EstadoCivil estadoCivil;

}
