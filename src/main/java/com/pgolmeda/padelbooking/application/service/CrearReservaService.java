package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.in.CrearReservaUseCase;
import com.pgolmeda.padelbooking.application.port.out.PistaRepository;
import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.domain.exception.PistaNoEncontradaException;
import com.pgolmeda.padelbooking.domain.exception.ReservaSolapadaException;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import org.springframework.stereotype.Service;

@Service
public class CrearReservaService implements CrearReservaUseCase {

    private final ReservaRepository reservaRepository;
    private final PistaRepository pistaRepository;

    public CrearReservaService(ReservaRepository reservaRepository, PistaRepository pistaRepository) {
        this.reservaRepository = reservaRepository;
        this.pistaRepository = pistaRepository;
    }

    @Override
    public Reserva crear(CrearReservaCommand command) {
        pistaRepository.buscarPorId(command.pistaId())
                .orElseThrow(() -> new PistaNoEncontradaException(command.pistaId()));

        // Se consulta en BD en vez de cargar todas las reservas de la pista en memoria.
        if (reservaRepository.existeSolapamiento(command.pistaId(), command.inicio(), command.fin())) {
            throw new ReservaSolapadaException(command.pistaId());
        }

        Reserva reserva = Reserva.nueva(command.pistaId(), command.clienteNombre(), command.inicio(), command.fin());
        return reservaRepository.guardar(reserva);
    }
}
