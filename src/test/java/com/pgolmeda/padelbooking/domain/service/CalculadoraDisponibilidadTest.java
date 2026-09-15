package com.pgolmeda.padelbooking.domain.service;

import com.pgolmeda.padelbooking.domain.model.EstadoReserva;
import com.pgolmeda.padelbooking.domain.model.FranjaHoraria;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculadoraDisponibilidadTest {

    private static final LocalDate FECHA = LocalDate.of(2026, 10, 1);

    @Test
    void sin_reservas_todo_el_horario_del_club_esta_libre() {
        List<FranjaHoraria> libres = CalculadoraDisponibilidad.calcular(FECHA, List.of());

        // 08:00 a 22:00 en franjas de 1h = 14 huecos
        assertEquals(14, libres.size());
        assertEquals(LocalDateTime.of(FECHA, java.time.LocalTime.of(8, 0)), libres.get(0).inicio());
    }

    @Test
    void una_reserva_bloquea_exactamente_su_franja() {
        LocalDateTime inicio = LocalDateTime.of(FECHA, java.time.LocalTime.of(10, 0));
        Reserva reserva = new Reserva(1L, 1L, 7L, inicio, inicio.plusHours(1), EstadoReserva.CONFIRMADA);

        List<FranjaHoraria> libres = CalculadoraDisponibilidad.calcular(FECHA, List.of(reserva));

        assertEquals(13, libres.size());
        assertFalse(libres.contains(new FranjaHoraria(inicio, inicio.plusHours(1))));
    }

    @Test
    void una_reserva_cancelada_no_bloquea_el_hueco() {
        // El repositorio ya filtra por CONFIRMADA antes de llegar aquí, pero la calculadora
        // no se fía y filtra también — este test prueba justo esa defensa.
        LocalDateTime inicio = LocalDateTime.of(FECHA, java.time.LocalTime.of(10, 0));
        Reserva reserva = new Reserva(1L, 1L, 7L, inicio, inicio.plusHours(1), EstadoReserva.CANCELADA);

        List<FranjaHoraria> libres = CalculadoraDisponibilidad.calcular(FECHA, List.of(reserva));

        assertTrue(libres.contains(new FranjaHoraria(inicio, inicio.plusHours(1))));
    }
}
