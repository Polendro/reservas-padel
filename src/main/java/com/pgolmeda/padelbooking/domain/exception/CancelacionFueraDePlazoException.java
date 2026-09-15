package com.pgolmeda.padelbooking.domain.exception;

public class CancelacionFueraDePlazoException extends RuntimeException {

    public CancelacionFueraDePlazoException(Long reservaId) {
        super("La reserva " + reservaId + " ya no se puede cancelar (menos de 2h de antelación)");
    }
}
