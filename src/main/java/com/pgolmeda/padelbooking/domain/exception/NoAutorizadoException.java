package com.pgolmeda.padelbooking.domain.exception;

public class NoAutorizadoException extends RuntimeException {

    public NoAutorizadoException() {
        super("No tienes permiso para hacer esto");
    }
}
