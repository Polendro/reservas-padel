package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence.mapper;

import com.pgolmeda.padelbooking.domain.model.Usuario;
import com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence.UsuarioJpaEntity;

public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static UsuarioJpaEntity aEntidad(Usuario usuario) {
        return new UsuarioJpaEntity(usuario.getId(), usuario.getEmail(), usuario.getPasswordHash());
    }

    public static Usuario aDominio(UsuarioJpaEntity entidad) {
        return new Usuario(entidad.getId(), entidad.getEmail(), entidad.getPasswordHash());
    }
}
