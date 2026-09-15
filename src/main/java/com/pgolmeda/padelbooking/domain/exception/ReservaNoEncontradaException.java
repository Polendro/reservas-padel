package com.pgolmeda.padelbooking.domain.exception;

public class ReservaNoEncontradaException extends RuntimeException {

    public ReservaNoEncontradaException(Long reservaId) {
        super("No existe la reserva con id " + reservaId);
    }
}
