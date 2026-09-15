package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.in.ListarMisReservasUseCase;
import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.application.port.out.UsuarioRepository;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import com.pgolmeda.padelbooking.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarMisReservasService implements ListarMisReservasUseCase {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;

    public ListarMisReservasService(ReservaRepository reservaRepository, UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Reserva> listar(String emailUsuario) {
        Usuario usuario = usuarioRepository.buscarPorEmail(emailUsuario)
                .orElseThrow(() -> new IllegalStateException(
                        "El usuario autenticado (" + emailUsuario + ") ya no existe"));

        return reservaRepository.buscarPorUsuarioId(usuario.getId());
    }
}
