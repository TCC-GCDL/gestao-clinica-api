package com.tcc.gestaoclinica.domain.exceptions;

public class CpfExistsException extends RuntimeException{
    public CpfExistsException() {
        super("CPF já cadastrado no sistemas");
    }

}
