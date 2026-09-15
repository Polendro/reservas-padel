package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.in.CancelarReservaUseCase;
import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.domain.exception.ReservaNoEncontradaException;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CancelarReservaService implements CancelarReservaUseCase {

    private final ReservaRepository reservaRepository;

    public CancelarReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Reserva cancelar(Long reservaId) {
        Reserva reserva = reservaRepository.buscarPorId(reservaId)
                .orElseThrow(() -> new ReservaNoEncontradaException(reservaId));

        // La validación (ya cancelada / fuera de plazo) vive en Reserva.cancelar(), no aquí.
        Reserva cancelada = reserva.cancelar(LocalDateTime.now());
        return reservaRepository.guardar(cancelada);
    }
}
