package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.application.port.out.UsuarioRepository;
import com.pgolmeda.padelbooking.domain.model.EstadoReserva;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import com.pgolmeda.padelbooking.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListarMisReservasServiceTest {

    private static final Usuario USUARIO = new Usuario(7L, "pablo@test.com", "hash-cualquiera");

    private ReservaRepository reservaRepository;
    private UsuarioRepository usuarioRepository;
    private ListarMisReservasService service;

    @BeforeEach
    void setUp() {
        reservaRepository = mock(ReservaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        service = new ListarMisReservasService(reservaRepository, usuarioRepository);
    }

    @Test
    void devuelve_las_reservas_del_usuario_autenticado() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        Reserva reserva = new Reserva(1L, 1L, USUARIO.getId(), inicio, inicio.plusHours(1), EstadoReserva.CONFIRMADA);
        when(usuarioRepository.buscarPorEmail(USUARIO.getEmail())).thenReturn(Optional.of(USUARIO));
        when(reservaRepository.buscarPorUsuarioId(USUARIO.getId())).thenReturn(List.of(reserva));

        List<Reserva> reservas = service.listar(USUARIO.getEmail());

        assertEquals(1, reservas.size());
        assertEquals(reserva, reservas.get(0));
    }
}
