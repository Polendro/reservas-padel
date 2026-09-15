package com.pgolmeda.padelbooking.application.port.out;

import com.pgolmeda.padelbooking.domain.model.Usuario;

import java.util.Optional;

// Puerto de salida: la aplicación necesita emitir y validar tokens, pero no le
// importa si por debajo es JWT, un token opaco en Redis, o lo que sea.
public interface TokenProvider {

    String generar(Usuario usuario);

    // Vacío si el token no es válido (expirado, manipulado, mal formado...).
    // Si es válido, devuelve el email del usuario al que pertenece.
    Optional<String> validarYObtenerEmail(String token);
}
