package com.pgolmeda.padelbooking.domain.exception;

public class ReservaSolapadaException extends RuntimeException {

    public ReservaSolapadaException(Long pistaId) {
        super("Ya existe una reserva que se solapa en la pista " + pistaId + " para ese horario");
    }
}
