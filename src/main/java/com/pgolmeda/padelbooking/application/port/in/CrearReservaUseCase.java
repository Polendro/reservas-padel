package com.pgolmeda.padelbooking.application.port.in;

import com.pgolmeda.padelbooking.domain.model.Reserva;

import java.time.LocalDateTime;

/**
 * Puerto de entrada: lo que un adaptador (REST, CLI, un test...) puede pedirle a la aplicación.
 * El controller depende de esta interfaz, no al revés.
 */
public interface CrearReservaUseCase {

    Reserva crear(CrearReservaCommand command);

    record CrearReservaCommand(Long pistaId, String clienteNombre, LocalDateTime inicio, LocalDateTime fin) {
    }
}
