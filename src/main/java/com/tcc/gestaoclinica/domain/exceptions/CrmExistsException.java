package com.tcc.gestaoclinica.domain.exceptions;

public class CrmExistsException extends RuntimeException{
    public CrmExistsException() {
        super("CRM já cadastrado no sistemas");
    }

}
