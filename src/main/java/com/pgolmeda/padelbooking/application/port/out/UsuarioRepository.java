package com.pgolmeda.padelbooking.application.port.out;

import com.pgolmeda.padelbooking.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository {

    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);
}
