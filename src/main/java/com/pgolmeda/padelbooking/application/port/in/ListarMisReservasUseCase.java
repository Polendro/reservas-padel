package com.pgolmeda.padelbooking.application.port.in;

import com.pgolmeda.padelbooking.domain.model.Reserva;

import java.util.List;

public interface ListarMisReservasUseCase {

    List<Reserva> listar(String emailUsuario);
}
