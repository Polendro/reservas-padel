package com.pgolmeda.padelbooking.domain.service;

import com.pgolmeda.padelbooking.domain.model.EstadoReserva;
import com.pgolmeda.padelbooking.domain.model.FranjaHoraria;
import com.pgolmeda.padelbooking.domain.model.Reserva;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Lógica que involucra varias reservas a la vez, no una sola: no encaja como método
// de Reserva ni de Pista, así que va como "domain service" (sigue sin depender de Spring/JPA).
public final class CalculadoraDisponibilidad {

    // Horario del club y duración de franja fijos por ahora; el día que haya que
    // configurarlos por pista o por club, es la primera pieza que hay que tocar.
    private static final LocalTime APERTURA = LocalTime.of(8, 0);
    private static final LocalTime CIERRE = LocalTime.of(22, 0);
    private static final Duration DURACION_FRANJA = Duration.ofHours(1);

    private CalculadoraDisponibilidad() {
    }

    public static List<FranjaHoraria> calcular(LocalDate fecha, List<Reserva> reservasDelDia) {
        List<FranjaHoraria> libres = new ArrayList<>();
        LocalDateTime cursor = LocalDateTime.of(fecha, APERTURA);
        LocalDateTime cierreDelDia = LocalDateTime.of(fecha, CIERRE);

        while (!cursor.plus(DURACION_FRANJA).isAfter(cierreDelDia)) {
            // cursor se reasigna al final del bucle, así que no vale para el lambda de abajo
            // (tiene que ser efectivamente final) — de ahí esta copia local por vuelta.
            LocalDateTime inicioFranja = cursor;
            LocalDateTime finFranja = cursor.plus(DURACION_FRANJA);

            // No confío en que quien llama ya haya filtrado por CONFIRMADA: lo compruebo aquí también.
            boolean ocupada = reservasDelDia.stream()
                    .filter(r -> r.getEstado() == EstadoReserva.CONFIRMADA)
                    .anyMatch(r -> r.seSolapaCon(inicioFranja, finFranja));
            if (!ocupada) {
                libres.add(new FranjaHoraria(inicioFranja, finFranja));
            }
            cursor = finFranja;
        }
        return libres;
    }
}
