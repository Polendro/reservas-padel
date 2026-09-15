package com.pgolmeda.padelbooking.infrastructure.adapter.in.web;

import com.pgolmeda.padelbooking.application.port.in.CancelarReservaUseCase;
import com.pgolmeda.padelbooking.application.port.in.CrearReservaUseCase;
import com.pgolmeda.padelbooking.application.port.in.CrearReservaUseCase.CrearReservaCommand;
import com.pgolmeda.padelbooking.application.port.in.ListarMisReservasUseCase;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto.CrearReservaRequest;
import com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto.ReservaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final CrearReservaUseCase crearReservaUseCase;
    private final CancelarReservaUseCase cancelarReservaUseCase;
    private final ListarMisReservasUseCase listarMisReservasUseCase;

    public ReservaController(CrearReservaUseCase crearReservaUseCase, CancelarReservaUseCase cancelarReservaUseCase,
                              ListarMisReservasUseCase listarMisReservasUseCase) {
        this.crearReservaUseCase = crearReservaUseCase;
        this.cancelarReservaUseCase = cancelarReservaUseCase;
        this.listarMisReservasUseCase = listarMisReservasUseCase;
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> crear(@Valid @RequestBody CrearReservaRequest request,
                                                  Authentication authentication) {
        // authentication.getName() es el email: lo dejó ahí JwtAuthenticationFilter al
        // validar el token, mucho antes de que la petición llegue aquí.
        Reserva reserva = crearReservaUseCase.crear(new CrearReservaCommand(
                request.pistaId(), authentication.getName(), request.inicio(), request.fin()));

        return ResponseEntity.status(HttpStatus.CREATED).body(ReservaResponse.desde(reserva));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponse> cancelar(@PathVariable Long id, Authentication authentication) {
        Reserva reserva = cancelarReservaUseCase.cancelar(id, authentication.getName());
        return ResponseEntity.ok(ReservaResponse.desde(reserva));
    }

    @GetMapping("/mias")
    public List<ReservaResponse> misReservas(Authentication authentication) {
        return listarMisReservasUseCase.listar(authentication.getName()).stream()
                .map(ReservaResponse::desde)
                .toList();
    }
}
