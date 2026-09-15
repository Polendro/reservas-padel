package com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto;

import com.pgolmeda.padelbooking.domain.model.FranjaHoraria;

import java.time.LocalDateTime;

public record FranjaDisponibleResponse(LocalDateTime inicio, LocalDateTime fin) {

    public static FranjaDisponibleResponse desde(FranjaHoraria franja) {
        return new FranjaDisponibleResponse(franja.inicio(), franja.fin());
    }
}
