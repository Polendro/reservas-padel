package com.pgolmeda.padelbooking.domain.model;

import com.pgolmeda.padelbooking.domain.exception.CancelacionFueraDePlazoException;
import com.pgolmeda.padelbooking.domain.exception.ReservaYaCanceladaException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReservaTest {

    @Test
    void no_deja_crear_una_reserva_con_fin_igual_o_anterior_al_inicio() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);

        assertThrows(IllegalArgumentException.class, () ->
                new Reserva(null, 1L, "Pablo", inicio, inicio, EstadoReserva.CONFIRMADA));
    }

    @Test
    void detecta_solapamiento_cuando_los_horarios_se_cruzan() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        Reserva reserva = new Reserva(1L, 1L, "Pablo", inicio, inicio.plusHours(1), EstadoReserva.CONFIRMADA);

        assertTrue(reserva.seSolapaCon(inicio.plusMinutes(30), inicio.plusHours(2)));
    }

    @Test
    void no_hay_solapamiento_si_una_empieza_justo_cuando_acaba_la_otra() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        Reserva reserva = new Reserva(1L, 1L, "Pablo", inicio, inicio.plusHours(1), EstadoReserva.CONFIRMADA);

        assertFalse(reserva.seSolapaCon(inicio.plusHours(1), inicio.plusHours(2)));
    }

    @Test
    void cancela_una_reserva_confirmada_con_antelacion_suficiente() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        Reserva reserva = new Reserva(1L, 1L, "Pablo", inicio, inicio.plusHours(1), EstadoReserva.CONFIRMADA);

        Reserva cancelada = reserva.cancelar(LocalDateTime.now());

        assertEquals(EstadoReserva.CANCELADA, cancelada.getEstado());
    }

    @Test
    void no_deja_cancelar_una_reserva_que_ya_estaba_cancelada() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        Reserva reserva = new Reserva(1L, 1L, "Pablo", inicio, inicio.plusHours(1), EstadoReserva.CANCELADA);

        assertThrows(ReservaYaCanceladaException.class, () -> reserva.cancelar(LocalDateTime.now()));
    }

    @Test
    void no_deja_cancelar_con_menos_de_dos_horas_de_antelacion() {
        LocalDateTime inicio = LocalDateTime.now().plusHours(1);
        Reserva reserva = new Reserva(1L, 1L, "Pablo", inicio, inicio.plusHours(1), EstadoReserva.CONFIRMADA);

        assertThrows(CancelacionFueraDePlazoException.class, () -> reserva.cancelar(LocalDateTime.now()));
    }
}
