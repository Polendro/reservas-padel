package com.pgolmeda.padelbooking.domain.exception;

public class EmailYaRegistradoException extends RuntimeException {

    public EmailYaRegistradoException(String email) {
        super("Ya existe una cuenta con el email " + email);
    }
}
