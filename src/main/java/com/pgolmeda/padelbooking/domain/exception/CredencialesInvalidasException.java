package com.pgolmeda.padelbooking.domain.exception;

public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException() {
        // Mensaje a propósito genérico: no decimos si el email no existe o si es
        // el password lo que falla. Si lo distinguimos, un atacante puede usar el
        // endpoint de login para averiguar qué emails están registrados.
        super("Email o contraseña incorrectos");
    }
}
