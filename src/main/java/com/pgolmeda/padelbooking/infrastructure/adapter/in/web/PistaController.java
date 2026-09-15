package com.pgolmeda.padelbooking.infrastructure.adapter.in.web;

import com.pgolmeda.padelbooking.application.port.in.ConsultarDisponibilidadUseCase;
import com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto.FranjaDisponibleResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pistas")
public class PistaController {

    private final ConsultarDisponibilidadUseCase consultarDisponibilidadUseCase;

    public PistaController(ConsultarDisponibilidadUseCase consultarDisponibilidadUseCase) {
        this.consultarDisponibilidadUseCase = consultarDisponibilidadUseCase;
    }

    // GET porque no cambia nada en el servidor: es idempotente y cacheable, a diferencia
    // de los endpoints de ReservaController (POST/PATCH).
    @GetMapping("/{id}/disponibilidad")
    public List<FranjaDisponibleResponse> disponibilidad(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        return consultarDisponibilidadUseCase.consultar(id, fecha).stream()
                .map(FranjaDisponibleResponse::desde)
                .toList();
    }
}
