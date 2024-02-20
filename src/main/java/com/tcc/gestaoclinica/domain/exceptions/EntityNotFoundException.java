package com.tcc.gestaoclinica.domain.exceptions;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String mensagem) {
        super(mensagem);
    }

}
