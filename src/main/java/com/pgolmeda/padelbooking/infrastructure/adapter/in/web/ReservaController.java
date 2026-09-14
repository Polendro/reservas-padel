package com.pgolmeda.padelbooking.infrastructure.adapter.in.web;

import com.pgolmeda.padelbooking.application.port.in.CrearReservaUseCase;
import com.pgolmeda.padelbooking.application.port.in.CrearReservaUseCase.CrearReservaCommand;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto.CrearReservaRequest;
import com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto.ReservaResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final CrearReservaUseCase crearReservaUseCase;

    public ReservaController(CrearReservaUseCase crearReservaUseCase) {
        this.crearReservaUseCase = crearReservaUseCase;
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> crear(@Valid @RequestBody CrearReservaRequest request) {
        Reserva reserva = crearReservaUseCase.crear(new CrearReservaCommand(
                request.pistaId(), request.clienteNombre(), request.inicio(), request.fin()));

        return ResponseEntity.status(HttpStatus.CREATED).body(ReservaResponse.desde(reserva));
    }
}
