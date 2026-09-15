package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.out.PistaRepository;
import com.pgolmeda.padelbooking.domain.model.Pista;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListarPistasServiceTest {

    @Test
    void devuelve_las_pistas_activas_del_repositorio() {
        PistaRepository pistaRepository = mock(PistaRepository.class);
        when(pistaRepository.listarActivas()).thenReturn(List.of(new Pista(1L, "Pista 1", true)));
        ListarPistasService service = new ListarPistasService(pistaRepository);

        List<Pista> pistas = service.listar();

        assertEquals(1, pistas.size());
        assertEquals("Pista 1", pistas.get(0).getNombre());
    }
}
