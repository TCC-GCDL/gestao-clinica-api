package com.tcc.gestaoclinica.domain.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("Já existe um usuário com esse email cadastrado no sistema.");
    }
}
