package com.pgolmeda.padelbooking.application.port.in;

import com.pgolmeda.padelbooking.domain.model.FranjaHoraria;

import java.time.LocalDate;
import java.util.List;

public interface ConsultarDisponibilidadUseCase {

    List<FranjaHoraria> consultar(Long pistaId, LocalDate fecha);
}
