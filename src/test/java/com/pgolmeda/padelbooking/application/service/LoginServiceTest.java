package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.in.LoginUseCase.LoginCommand;
import com.pgolmeda.padelbooking.application.port.out.PasswordHasher;
import com.pgolmeda.padelbooking.application.port.out.TokenProvider;
import com.pgolmeda.padelbooking.application.port.out.UsuarioRepository;
import com.pgolmeda.padelbooking.domain.exception.CredencialesInvalidasException;
import com.pgolmeda.padelbooking.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class LoginServiceTest {

    private UsuarioRepository usuarioRepository;
    private PasswordHasher passwordHasher;
    private TokenProvider tokenProvider;
    private LoginService service;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        passwordHasher = mock(PasswordHasher.class);
        tokenProvider = mock(TokenProvider.class);
        service = new LoginService(usuarioRepository, passwordHasher, tokenProvider);
    }

    @Test
    void devuelve_el_token_si_las_credenciales_son_correctas() {
        Usuario usuario = Usuario.nuevo("pablo@test.com", "hash-real");
        when(usuarioRepository.buscarPorEmail("pablo@test.com")).thenReturn(Optional.of(usuario));
        when(passwordHasher.coincide("password123", "hash-real")).thenReturn(true);
        when(tokenProvider.generar(usuario)).thenReturn("token-falso");

        String token = service.login(new LoginCommand("pablo@test.com", "password123"));

        assertEquals("token-falso", token);
    }

    @Test
    void falla_con_el_mismo_error_si_el_email_no_existe() {
        when(usuarioRepository.buscarPorEmail("noexiste@test.com")).thenReturn(Optional.empty());

        assertThrows(CredencialesInvalidasException.class, () ->
                service.login(new LoginCommand("noexiste@test.com", "cualquiera")));

        verifyNoInteractions(tokenProvider);
    }

    @Test
    void falla_con_el_mismo_error_si_el_password_no_coincide() {
        Usuario usuario = Usuario.nuevo("pablo@test.com", "hash-real");
        when(usuarioRepository.buscarPorEmail("pablo@test.com")).thenReturn(Optional.of(usuario));
        when(passwordHasher.coincide("password-incorrecto", "hash-real")).thenReturn(false);

        assertThrows(CredencialesInvalidasException.class, () ->
                service.login(new LoginCommand("pablo@test.com", "password-incorrecto")));

        verifyNoInteractions(tokenProvider);
    }
}
