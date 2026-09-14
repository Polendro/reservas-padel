package com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto;

import com.pgolmeda.padelbooking.domain.model.EstadoReserva;
import com.pgolmeda.padelbooking.domain.model.Reserva;

import java.time.LocalDateTime;

public record ReservaResponse(
        Long id,
        Long pistaId,
        String clienteNombre,
        LocalDateTime inicio,
        LocalDateTime fin,
        EstadoReserva estado) {

    public static ReservaResponse desde(Reserva reserva) {
        return new ReservaResponse(
                reserva.getId(),
                reserva.getPistaId(),
                reserva.getClienteNombre(),
                reserva.getInicio(),
                reserva.getFin(),
                reserva.getEstado());
    }
}
