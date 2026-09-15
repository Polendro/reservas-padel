package com.pgolmeda.padelbooking.domain.exception;

public class ReservaYaCanceladaException extends RuntimeException {

    public ReservaYaCanceladaException(Long reservaId) {
        super("La reserva " + reservaId + " ya estaba cancelada");
    }
}
