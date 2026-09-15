package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.in.RegistrarUsuarioUseCase;
import com.pgolmeda.padelbooking.application.port.out.PasswordHasher;
import com.pgolmeda.padelbooking.application.port.out.UsuarioRepository;
import com.pgolmeda.padelbooking.domain.exception.EmailYaRegistradoException;
import com.pgolmeda.padelbooking.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class RegistrarUsuarioService implements RegistrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;

    public RegistrarUsuarioService(UsuarioRepository usuarioRepository, PasswordHasher passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Usuario registrar(RegistrarUsuarioCommand command) {
        usuarioRepository.buscarPorEmail(command.email()).ifPresent(u -> {
            throw new EmailYaRegistradoException(command.email());
        });

        String hash = passwordHasher.hash(command.password());
        return usuarioRepository.guardar(Usuario.nuevo(command.email(), hash));
    }
}
