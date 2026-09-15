package com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto;

import com.pgolmeda.padelbooking.domain.model.Pista;

public record PistaResponse(Long id, String nombre) {

    public static PistaResponse desde(Pista pista) {
        return new PistaResponse(pista.getId(), pista.getNombre());
    }
}
