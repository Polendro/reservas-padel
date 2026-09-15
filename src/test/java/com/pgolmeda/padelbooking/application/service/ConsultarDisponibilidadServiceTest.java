package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.out.PistaRepository;
import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.domain.exception.PistaNoEncontradaException;
import com.pgolmeda.padelbooking.domain.model.FranjaHoraria;
import com.pgolmeda.padelbooking.domain.model.Pista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class ConsultarDisponibilidadServiceTest {

    private static final LocalDate FECHA = LocalDate.of(2026, 10, 1);

    private ReservaRepository reservaRepository;
    private PistaRepository pistaRepository;
    private ConsultarDisponibilidadService service;

    @BeforeEach
    void setUp() {
        reservaRepository = mock(ReservaRepository.class);
        pistaRepository = mock(PistaRepository.class);
        service = new ConsultarDisponibilidadService(reservaRepository, pistaRepository);
    }

    @Test
    void devuelve_las_franjas_libres_cuando_la_pista_existe() {
        when(pistaRepository.buscarPorId(1L)).thenReturn(Optional.of(new Pista(1L, "Pista 1", true)));
        when(reservaRepository.buscarConfirmadasPorPistaYFecha(1L, FECHA)).thenReturn(List.of());

        List<FranjaHoraria> libres = service.consultar(1L, FECHA);

        assertEquals(14, libres.size());
    }

    @Test
    void falla_si_la_pista_no_existe_y_ni_siquiera_consulta_las_reservas() {
        when(pistaRepository.buscarPorId(1L)).thenReturn(Optional.empty());

        assertThrows(PistaNoEncontradaException.class, () -> service.consultar(1L, FECHA));

        verifyNoInteractions(reservaRepository);
    }
}
