package com.pgolmeda.padelbooking.domain.model;

public class Pista {

    private final Long id;
    private final String nombre;
    private final boolean activa;

    public Pista(Long id, String nombre, boolean activa) {
        this.id = id;
        this.nombre = nombre;
        this.activa = activa;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isActiva() {
        return activa;
    }
}
