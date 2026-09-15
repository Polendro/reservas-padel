package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.in.RegistrarUsuarioUseCase.RegistrarUsuarioCommand;
import com.pgolmeda.padelbooking.application.port.out.PasswordHasher;
import com.pgolmeda.padelbooking.application.port.out.UsuarioRepository;
import com.pgolmeda.padelbooking.domain.exception.EmailYaRegistradoException;
import com.pgolmeda.padelbooking.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegistrarUsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private PasswordHasher passwordHasher;
    private RegistrarUsuarioService service;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        passwordHasher = mock(PasswordHasher.class);
        service = new RegistrarUsuarioService(usuarioRepository, passwordHasher);
    }

    @Test
    void registra_el_usuario_con_el_password_hasheado() {
        when(usuarioRepository.buscarPorEmail("pablo@test.com")).thenReturn(Optional.empty());
        when(passwordHasher.hash("password123")).thenReturn("hash-falso");
        when(usuarioRepository.guardar(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario usuario = service.registrar(new RegistrarUsuarioCommand("pablo@test.com", "password123"));

        assertEquals("pablo@test.com", usuario.getEmail());
        assertEquals("hash-falso", usuario.getPasswordHash());
    }

    @Test
    void falla_si_el_email_ya_esta_registrado() {
        when(usuarioRepository.buscarPorEmail("pablo@test.com"))
                .thenReturn(Optional.of(Usuario.nuevo("pablo@test.com", "hash-existente")));

        assertThrows(EmailYaRegistradoException.class, () ->
                service.registrar(new RegistrarUsuarioCommand("pablo@test.com", "password123")));

        verify(usuarioRepository, never()).guardar(any());
    }
}
