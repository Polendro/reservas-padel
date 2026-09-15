package com.pgolmeda.padelbooking.application.port.in;

public interface LoginUseCase {

    // Devuelve el token JWT ya listo para mandar al cliente. El propio JWT lleva
    // dentro la fecha de expiración (claim "exp"), así que no hace falta devolverla aparte.
    String login(LoginCommand command);

    record LoginCommand(String email, String password) {
    }
}
