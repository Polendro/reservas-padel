package com.pgolmeda.padelbooking.application.port.out;

import com.pgolmeda.padelbooking.domain.model.Pista;

import java.util.List;
import java.util.Optional;

public interface PistaRepository {

    Optional<Pista> buscarPorId(Long id);

    List<Pista> listarActivas();
}
