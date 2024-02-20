package com.tcc.gestaoclinica.api.dto;

import com.tcc.gestaoclinica.domain.models.CategoriaCNH;
import com.tcc.gestaoclinica.domain.models.EstadoCivil;
import com.tcc.gestaoclinica.domain.models.RoleName;
import com.tcc.gestaoclinica.domain.models.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PacienteDto {

    @NotBlank
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String sobrenome;

    @NotBlank
    private String telefone;

    @NotBlank
    private String cidade;

    @NotBlank
    private String cpf;

    @NotBlank
    private String genero;

    private LocalDate dataDeNascimento;

    @NotBlank
    private String cep;

    @NotBlank
    private String rua;

    @NotBlank
    private String numero;

    private String complemento;

    @NotBlank
    private String bairro;

    @NotBlank
    private String estado;

    @NotBlank
    private String email;

    @NotBlank
    private String renach;

    @NotBlank
    private CategoriaCNH categoriaCNH;

    @NotBlank
    private EstadoCivil estadoCivil;






}
