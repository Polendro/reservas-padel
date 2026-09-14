package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence.mapper;

import com.pgolmeda.padelbooking.domain.model.Reserva;
import com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence.ReservaJpaEntity;

// Convierte entre el dominio y la entidad JPA.
public final class ReservaMapper {

    private ReservaMapper() {
    }

    public static ReservaJpaEntity aEntidad(Reserva reserva) {
        return new ReservaJpaEntity(
                reserva.getId(),
                reserva.getPistaId(),
                reserva.getClienteNombre(),
                reserva.getInicio(),
                reserva.getFin(),
                reserva.getEstado());
    }

    public static Reserva aDominio(ReservaJpaEntity entidad) {
        return new Reserva(
                entidad.getId(),
                entidad.getPistaId(),
                entidad.getClienteNombre(),
                entidad.getInicio(),
                entidad.getFin(),
                entidad.getEstado());
    }
}
