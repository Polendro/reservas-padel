package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.domain.exception.ReservaNoEncontradaException;
import com.pgolmeda.padelbooking.domain.model.EstadoReserva;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CancelarReservaServiceTest {

    private ReservaRepository reservaRepository;
    private CancelarReservaService service;

    @BeforeEach
    void setUp() {
        reservaRepository = mock(ReservaRepository.class);
        service = new CancelarReservaService(reservaRepository);
    }

    @Test
    void cancela_la_reserva_si_existe_y_puede_cancelarse() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        Reserva reserva = new Reserva(1L, 1L, 7L, inicio, inicio.plusHours(1), EstadoReserva.CONFIRMADA);
        when(reservaRepository.buscarPorId(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.guardar(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reserva cancelada = service.cancelar(1L);

        assertEquals(EstadoReserva.CANCELADA, cancelada.getEstado());
    }

    @Test
    void falla_si_la_reserva_no_existe() {
        when(reservaRepository.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(ReservaNoEncontradaException.class, () -> service.cancelar(1L));
    }
}
