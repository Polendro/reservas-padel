package com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

// Ya no lleva quién hace la reserva: eso sale del token, no de algo que el cliente
// pueda escribir a mano (antes cualquiera podía reservar "a nombre de" quien quisiera).
public record CrearReservaRequest(
        @NotNull Long pistaId,
        @NotNull @Future LocalDateTime inicio,
        @NotNull @Future LocalDateTime fin) {
}
