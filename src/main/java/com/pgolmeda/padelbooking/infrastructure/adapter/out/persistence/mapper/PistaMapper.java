package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence.mapper;

import com.pgolmeda.padelbooking.domain.model.Pista;
import com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence.PistaJpaEntity;

public final class PistaMapper {

    private PistaMapper() {
    }

    public static Pista aDominio(PistaJpaEntity entidad) {
        return new Pista(entidad.getId(), entidad.getNombre(), entidad.isActiva());
    }
}
