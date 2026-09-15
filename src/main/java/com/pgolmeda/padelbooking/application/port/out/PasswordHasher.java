package com.pgolmeda.padelbooking.application.port.out;

// Puerto de salida igual que un repositorio, aunque no hable con una BD: la aplicación
// necesita hashear y comparar contraseñas, pero no le importa si por debajo es BCrypt,
// Argon2 o lo que sea — eso lo decide el adaptador.
public interface PasswordHasher {

    String hash(String rawPassword);

    boolean coincide(String rawPassword, String hash);
}
