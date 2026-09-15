package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.in.CrearReservaUseCase.CrearReservaCommand;
import com.pgolmeda.padelbooking.application.port.out.PistaRepository;
import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.application.port.out.UsuarioRepository;
import com.pgolmeda.padelbooking.domain.exception.PistaNoEncontradaException;
import com.pgolmeda.padelbooking.domain.exception.ReservaSolapadaException;
import com.pgolmeda.padelbooking.domain.model.Pista;
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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

// Test unitario puro: los puertos van mockeados, no hace falta levantar Spring ni BD.
class CrearReservaServiceTest {

    private static final Usuario USUARIO = new Usuario(7L, "pablo@test.com", "hash-cualquiera");

    private ReservaRepository reservaRepository;
    private PistaRepository pistaRepository;
    private UsuarioRepository usuarioRepository;
    private CrearReservaService service;

    @BeforeEach
    void setUp() {
        reservaRepository = mock(ReservaRepository.class);
        pistaRepository = mock(PistaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        service = new CrearReservaService(reservaRepository, pistaRepository, usuarioRepository);

        when(usuarioRepository.buscarPorEmail("pablo@test.com")).thenReturn(Optional.of(USUARIO));
    }

    @Test
    void crea_la_reserva_si_la_pista_existe_y_no_hay_solapamiento() {
        Long pistaId = 1L;
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = inicio.plusHours(1);
        when(pistaRepository.buscarPorId(pistaId)).thenReturn(Optional.of(new Pista(pistaId, "Pista 1", true)));
        when(reservaRepository.existeSolapamiento(pistaId, inicio, fin)).thenReturn(false);
        when(reservaRepository.guardar(any(Reserva.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reserva reserva = service.crear(new CrearReservaCommand(pistaId, "pablo@test.com", inicio, fin));

        assertEquals(pistaId, reserva.getPistaId());
        assertEquals(USUARIO.getId(), reserva.getUsuarioId());
        verify(reservaRepository).guardar(any(Reserva.class));
    }

    @Test
    void falla_si_la_pista_no_existe() {
        when(pistaRepository.buscarPorId(1L)).thenReturn(Optional.empty());
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);

        assertThrows(PistaNoEncontradaException.class, () ->
                service.crear(new CrearReservaCommand(1L, "pablo@test.com", inicio, inicio.plusHours(1))));

        verifyNoInteractions(reservaRepository);
    }

    @Test
    void falla_si_ya_hay_una_reserva_solapada() {
        Long pistaId = 1L;
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = inicio.plusHours(1);
        when(pistaRepository.buscarPorId(pistaId)).thenReturn(Optional.of(new Pista(pistaId, "Pista 1", true)));
        when(reservaRepository.existeSolapamiento(pistaId, inicio, fin)).thenReturn(true);

        assertThrows(ReservaSolapadaException.class, () ->
                service.crear(new CrearReservaCommand(pistaId, "pablo@test.com", inicio, fin)));

        verify(reservaRepository, never()).guardar(any());
    }
}
