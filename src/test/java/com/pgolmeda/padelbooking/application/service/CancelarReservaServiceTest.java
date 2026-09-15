package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.application.port.out.UsuarioRepository;
import com.pgolmeda.padelbooking.domain.exception.NoAutorizadoException;
import com.pgolmeda.padelbooking.domain.exception.ReservaNoEncontradaException;
import com.pgolmeda.padelbooking.domain.model.EstadoReserva;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import com.pgolmeda.padelbooking.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CancelarReservaServiceTest {

    private static final Usuario DUENO = new Usuario(7L, "pablo@test.com", "hash-cualquiera");
    private static final Usuario OTRO = new Usuario(8L, "otro@test.com", "hash-cualquiera");

    private ReservaRepository reservaRepository;
    private UsuarioRepository usuarioRepository;
    private CancelarReservaService service;

    @BeforeEach
    void setUp() {
        reservaRepository = mock(ReservaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        service = new CancelarReservaService(reservaRepository, usuarioRepository);
    }

    @Test
    void cancela_la_reserva_si_existe_y_el_dueno_la_cancela() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        Reserva reserva = new Reserva(1L, 1L, DUENO.getId(), inicio, inicio.plusHours(1), EstadoReserva.CONFIRMADA);
        when(reservaRepository.buscarPorId(1L)).thenReturn(Optional.of(reserva));
        when(usuarioRepository.buscarPorEmail(DUENO.getEmail())).thenReturn(Optional.of(DUENO));
        when(reservaRepository.guardar(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reserva cancelada = service.cancelar(1L, DUENO.getEmail());

        assertEquals(EstadoReserva.CANCELADA, cancelada.getEstado());
    }

    @Test
    void falla_si_la_reserva_no_existe() {
        when(reservaRepository.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(ReservaNoEncontradaException.class, () -> service.cancelar(1L, DUENO.getEmail()));
    }

    @Test
    void falla_si_quien_cancela_no_es_el_dueno_de_la_reserva() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        Reserva reserva = new Reserva(1L, 1L, DUENO.getId(), inicio, inicio.plusHours(1), EstadoReserva.CONFIRMADA);
        when(reservaRepository.buscarPorId(1L)).thenReturn(Optional.of(reserva));
        when(usuarioRepository.buscarPorEmail(OTRO.getEmail())).thenReturn(Optional.of(OTRO));

        assertThrows(NoAutorizadoException.class, () -> service.cancelar(1L, OTRO.getEmail()));

        verify(reservaRepository, never()).guardar(any());
    }
}
