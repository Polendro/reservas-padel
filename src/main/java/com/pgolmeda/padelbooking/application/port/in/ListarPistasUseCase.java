package com.pgolmeda.padelbooking.application.port.in;

import com.pgolmeda.padelbooking.domain.model.Pista;

import java.util.List;

public interface ListarPistasUseCase {

    List<Pista> listar();
}
