package com.pgolmeda.padelbooking.infrastructure.adapter.in.web.dto;

import com.pgolmeda.padelbooking.domain.model.Usuario;

// Ojo: aquí NO va passwordHash. Aunque esté hasheado, no hay ninguna razón para
// devolverlo en la respuesta HTTP.
public record UsuarioResponse(Long id, String email) {

    public static UsuarioResponse desde(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getEmail());
    }
}
