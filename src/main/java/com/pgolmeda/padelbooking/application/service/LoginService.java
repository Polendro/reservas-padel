package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.in.LoginUseCase;
import com.pgolmeda.padelbooking.application.port.out.PasswordHasher;
import com.pgolmeda.padelbooking.application.port.out.TokenProvider;
import com.pgolmeda.padelbooking.application.port.out.UsuarioRepository;
import com.pgolmeda.padelbooking.domain.exception.CredencialesInvalidasException;
import com.pgolmeda.padelbooking.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;
    private final TokenProvider tokenProvider;

    public LoginService(UsuarioRepository usuarioRepository, PasswordHasher passwordHasher,
                         TokenProvider tokenProvider) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public String login(LoginCommand command) {
        // Mismo mensaje de error tanto si el email no existe como si el password
        // no coincide: CredencialesInvalidasException() no distingue el motivo.
        Usuario usuario = usuarioRepository.buscarPorEmail(command.email())
                .orElseThrow(CredencialesInvalidasException::new);

        if (!passwordHasher.coincide(command.password(), usuario.getPasswordHash())) {
            throw new CredencialesInvalidasException();
        }

        return tokenProvider.generar(usuario);
    }
}
