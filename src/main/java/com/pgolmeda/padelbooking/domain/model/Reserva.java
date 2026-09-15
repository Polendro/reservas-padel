package com.pgolmeda.padelbooking.domain.model;

import com.pgolmeda.padelbooking.domain.exception.CancelacionFueraDePlazoException;
import com.pgolmeda.padelbooking.domain.exception.ReservaYaCanceladaException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

// Sin dependencias de Spring/JPA a propósito: se testea sin levantar contexto.
public class Reserva {

    private static final int HORAS_MINIMAS_DE_ANTELACION = 2;

    private final Long id;
    private final Long pistaId;
    private final Long usuarioId;
    private final LocalDateTime inicio;
    private final LocalDateTime fin;
    private final EstadoReserva estado;

    public Reserva(Long id, Long pistaId, Long usuarioId, LocalDateTime inicio, LocalDateTime fin,
                    EstadoReserva estado) {
        if (!inicio.isBefore(fin)) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin");
        }
        this.id = id;
        this.pistaId = pistaId;
        this.usuarioId = Objects.requireNonNull(usuarioId, "usuarioId no puede ser nulo");
        this.inicio = inicio;
        this.fin = fin;
        this.estado = estado;
    }

    /** Fábrica para una reserva nueva: sin id todavía (lo asigna la base de datos) y ya confirmada. */
    public static Reserva nueva(Long pistaId, Long usuarioId, LocalDateTime inicio, LocalDateTime fin) {
        return new Reserva(null, pistaId, usuarioId, inicio, fin, EstadoReserva.CONFIRMADA);
    }

    // Evita que dos reservas se crucen en la misma pista.
    public boolean seSolapaCon(LocalDateTime otroInicio, LocalDateTime otroFin) {
        return inicio.isBefore(otroFin) && otroInicio.isBefore(fin);
    }

    // "ahora" entra como parámetro (en vez de usar LocalDateTime.now() aquí dentro) para que
    // el test pueda fijar la hora y no dependa del reloj real.
    public Reserva cancelar(LocalDateTime ahora) {
        if (estado == EstadoReserva.CANCELADA) {
            throw new ReservaYaCanceladaException(id);
        }
        if (Duration.between(ahora, inicio).toHours() < HORAS_MINIMAS_DE_ANTELACION) {
            throw new CancelacionFueraDePlazoException(id);
        }
        return new Reserva(id, pistaId, usuarioId, inicio, fin, EstadoReserva.CANCELADA);
    }

    public Long getId() {
        return id;
    }

    public Long getPistaId() {
        return pistaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
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
