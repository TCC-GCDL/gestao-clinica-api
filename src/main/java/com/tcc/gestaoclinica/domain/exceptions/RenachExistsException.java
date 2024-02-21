package com.tcc.gestaoclinica.domain.exceptions;

public class RenachExistsException extends RuntimeException{
    public RenachExistsException() {
        super("Renach já cadastrado no sistema");
    }

}
