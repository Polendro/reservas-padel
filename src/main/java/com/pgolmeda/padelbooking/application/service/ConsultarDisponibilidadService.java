package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.in.ConsultarDisponibilidadUseCase;
import com.pgolmeda.padelbooking.application.port.out.PistaRepository;
import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.domain.exception.PistaNoEncontradaException;
import com.pgolmeda.padelbooking.domain.model.FranjaHoraria;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import com.pgolmeda.padelbooking.domain.service.CalculadoraDisponibilidad;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConsultarDisponibilidadService implements ConsultarDisponibilidadUseCase {

    private final ReservaRepository reservaRepository;
    private final PistaRepository pistaRepository;

    public ConsultarDisponibilidadService(ReservaRepository reservaRepository, PistaRepository pistaRepository) {
        this.reservaRepository = reservaRepository;
        this.pistaRepository = pistaRepository;
    }

    @Override
    public List<FranjaHoraria> consultar(Long pistaId, LocalDate fecha) {
        pistaRepository.buscarPorId(pistaId)
                .orElseThrow(() -> new PistaNoEncontradaException(pistaId));

        List<Reserva> reservasDelDia = reservaRepository.buscarConfirmadasPorPistaYFecha(pistaId, fecha);
        return CalculadoraDisponibilidad.calcular(fecha, reservasDelDia);
    }
}
