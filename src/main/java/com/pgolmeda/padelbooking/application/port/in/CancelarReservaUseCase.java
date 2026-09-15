package com.pgolmeda.padelbooking.application.port.in;

import com.pgolmeda.padelbooking.domain.model.Reserva;

public interface CancelarReservaUseCase {

    // emailUsuario es quien pide la cancelación (sale del token) — no tiene por qué
    // ser el dueño de la reserva; eso lo comprueba el servicio.
    Reserva cancelar(Long reservaId, String emailUsuario);
}
