package com.pgolmeda.padelbooking.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

// Sin dependencias de Spring/JPA a propósito: se testea sin levantar contexto.
public class Reserva {

    private final Long id;
    private final Long pistaId;
    private final String clienteNombre;
    private final LocalDateTime inicio;
    private final LocalDateTime fin;
    private final EstadoReserva estado;

    public Reserva(Long id, Long pistaId, String clienteNombre, LocalDateTime inicio, LocalDateTime fin,
                    EstadoReserva estado) {
        if (!inicio.isBefore(fin)) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin");
        }
        this.id = id;
        this.pistaId = pistaId;
        this.clienteNombre = Objects.requireNonNull(clienteNombre, "clienteNombre no puede ser nulo");
        this.inicio = inicio;
        this.fin = fin;
        this.estado = estado;
    }

    /** Fábrica para una reserva nueva: sin id todavía (lo asigna la base de datos) y ya confirmada. */
    public static Reserva nueva(Long pistaId, String clienteNombre, LocalDateTime inicio, LocalDateTime fin) {
        return new Reserva(null, pistaId, clienteNombre, inicio, fin, EstadoReserva.CONFIRMADA);
    }

    // Evita que dos reservas se crucen en la misma pista.
    public boolean seSolapaCon(LocalDateTime otroInicio, LocalDateTime otroFin) {
        return inicio.isBefore(otroFin) && otroInicio.isBefore(fin);
    }

    public Long getId() {
        return id;
    }

    public Long getPistaId() {
        return pistaId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public EstadoReserva getEstado() {
        return estado;
    }
}
