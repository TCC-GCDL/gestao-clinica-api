package com.tcc.gestaoclinica.domain.exceptions;

public class TelefoneExistsException extends RuntimeException{
    public TelefoneExistsException() {
        super("Telefone já cadastrado no sistemas");
    }

}
