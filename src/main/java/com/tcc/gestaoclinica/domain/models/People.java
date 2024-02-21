package com.tcc.gestaoclinica.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String cpf;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="endereco_id")
    private Address address;
    private String genero;
    private LocalDate dataDeNascimento;
}
