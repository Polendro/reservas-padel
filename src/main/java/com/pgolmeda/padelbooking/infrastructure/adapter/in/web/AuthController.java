package com.pgolmeda.padelbooking.infrastructure.adapter.in.web;

import com.pgolmeda.padelbooking.application.port.in.RegistrarUsuarioUseCase;
import com.pgolmeda.padelbooking.application.port.in.RegistrarUsuarioUseCase.RegistrarUsuarioCommand;
import com.pgolmeda.padelbooking.domain.model.Usuario;
import com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto.RegistrarUsuarioRequest;
import com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto.UsuarioResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;

    public AuthController(RegistrarUsuarioUseCase registrarUsuarioUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody RegistrarUsuarioRequest request) {
        Usuario usuario = registrarUsuarioUseCase.registrar(new RegistrarUsuarioCommand(request.email(), request.password()));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponse.desde(usuario));
    }
}
