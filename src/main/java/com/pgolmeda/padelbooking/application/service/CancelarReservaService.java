package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.in.CancelarReservaUseCase;
import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.application.port.out.UsuarioRepository;
import com.pgolmeda.padelbooking.domain.exception.NoAutorizadoException;
import com.pgolmeda.padelbooking.domain.exception.ReservaNoEncontradaException;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import com.pgolmeda.padelbooking.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CancelarReservaService implements CancelarReservaUseCase {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;

    public CancelarReservaService(ReservaRepository reservaRepository, UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Reserva cancelar(Long reservaId, String emailUsuario) {
        Reserva reserva = reservaRepository.buscarPorId(reservaId)
                .orElseThrow(() -> new ReservaNoEncontradaException(reservaId));

        Usuario usuario = usuarioRepository.buscarPorEmail(emailUsuario)
                .orElseThrow(() -> new IllegalStateException(
                        "El usuario autenticado (" + emailUsuario + ") ya no existe"));

        // No se distingue "no existe" de "no es tuya" con un 404 aquí a propósito:
        // esto es autorización (¿puedes?), no búsqueda (¿existe?). 403, no 404.
        if (!reserva.perteneceA(usuario.getId())) {
            throw new NoAutorizadoException();
        }

        // La validación de negocio (ya cancelada / fuera de plazo) vive en Reserva.cancelar().
        Reserva cancelada = reserva.cancelar(LocalDateTime.now());
        return reservaRepository.guardar(cancelada);
    }
}
