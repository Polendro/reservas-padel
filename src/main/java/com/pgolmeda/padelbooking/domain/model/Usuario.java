package com.pgolmeda.padelbooking.domain.model;

import java.util.Objects;

// passwordHash ya llega hasheado desde fuera (el dominio no sabe de BCrypt ni de
// ningún algoritmo concreto — eso es cosa del adaptador que implementa PasswordHasher).
public class Usuario {

    private final Long id;
    private final String email;
    private final String passwordHash;

    public Usuario(Long id, String email, String passwordHash) {
        this.id = id;
        this.email = Objects.requireNonNull(email, "email no puede ser nulo");
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash no puede ser nulo");
    }

    public static Usuario nuevo(String email, String passwordHash) {
        return new Usuario(null, email, passwordHash);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
