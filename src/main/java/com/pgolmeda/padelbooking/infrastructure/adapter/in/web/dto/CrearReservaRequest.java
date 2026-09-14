package com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

// DTO de entrada, separado del dominio para no acoplar el JSON al modelo de negocio.
public record CrearReservaRequest(
        @NotNull Long pistaId,
        @NotBlank String clienteNombre,
        @NotNull @Future LocalDateTime inicio,
        @NotNull @Future LocalDateTime fin) {
}
