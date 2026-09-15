package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.in.CrearReservaUseCase;
import com.pgolmeda.padelbooking.application.port.out.PistaRepository;
import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.application.port.out.UsuarioRepository;
import com.pgolmeda.padelbooking.domain.exception.PistaNoEncontradaException;
import com.pgolmeda.padelbooking.domain.exception.ReservaSolapadaException;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import com.pgolmeda.padelbooking.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class CrearReservaService implements CrearReservaUseCase {

    private final ReservaRepository reservaRepository;
    private final PistaRepository pistaRepository;
    private final UsuarioRepository usuarioRepository;

    public CrearReservaService(ReservaRepository reservaRepository, PistaRepository pistaRepository,
                                UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.pistaRepository = pistaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Reserva crear(CrearReservaCommand command) {
        pistaRepository.buscarPorId(command.pistaId())
                .orElseThrow(() -> new PistaNoEncontradaException(command.pistaId()));

        // Si el token es válido pero el usuario ya no existe (se borró la cuenta, por ejemplo),
        // esto no debería pasar en condiciones normales, pero mejor un error claro que un NPE.
        Usuario usuario = usuarioRepository.buscarPorEmail(command.emailUsuario())
                .orElseThrow(() -> new IllegalStateException(
                        "El usuario autenticado (" + command.emailUsuario() + ") ya no existe"));

        // Se consulta en BD en vez de cargar todas las reservas de la pista en memoria.
        if (reservaRepository.existeSolapamiento(command.pistaId(), command.inicio(), command.fin())) {
            throw new ReservaSolapadaException(command.pistaId());
        }

        Reserva reserva = Reserva.nueva(command.pistaId(), usuario.getId(), command.inicio(), command.fin());
        return reservaRepository.guardar(reserva);
    }
}
