package com.pgolmeda.padelbooking.domain.exception;

public class PistaNoEncontradaException extends RuntimeException {

    public PistaNoEncontradaException(Long pistaId) {
        super("No existe la pista con id " + pistaId);
    }
}
