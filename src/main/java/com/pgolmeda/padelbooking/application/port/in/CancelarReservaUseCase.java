package com.pgolmeda.padelbooking.application.port.in;

import com.pgolmeda.padelbooking.domain.model.Reserva;

public interface CancelarReservaUseCase {

    Reserva cancelar(Long reservaId);
}
