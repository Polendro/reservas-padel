package com.pgolmeda.padelbooking.application.port.in;

import com.pgolmeda.padelbooking.domain.model.Usuario;

public interface RegistrarUsuarioUseCase {

    Usuario registrar(RegistrarUsuarioCommand command);

    // El password va aquí en texto plano: todavía no se ha hasheado.
    // Eso lo hace el servicio, llamando a PasswordHasher.
    record RegistrarUsuarioCommand(String email, String password) {
    }
}
