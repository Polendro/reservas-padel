package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence;

import com.pgolmeda.padelbooking.domain.model.EstadoReserva;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

// Reutilizo el enum de dominio en vez de duplicarlo en uno propio de JPA (menos mapeo que mantener).
@Entity
@Table(name = "reservas")
public class ReservaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pista_id", nullable = false)
    private Long pistaId;

    @Column(name = "cliente_nombre", nullable = false)
    private String clienteNombre;

    @Column(nullable = false)
    private LocalDateTime inicio;

    @Column(nullable = false)
    private LocalDateTime fin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estado;

    protected ReservaJpaEntity() {
    }

    public ReservaJpaEntity(Long id, Long pistaId, String clienteNombre, LocalDateTime inicio, LocalDateTime fin,
                             EstadoReserva estado) {
        this.id = id;
        this.pistaId = pistaId;
        this.clienteNombre = clienteNombre;
        this.inicio = inicio;
        this.fin = fin;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public Long getPistaId() {
        return pistaId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public EstadoReserva getEstado() {
        return estado;
    }
}
