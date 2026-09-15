package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Solo mapeo a tabla; la lógica de negocio va en domain.model.Pista.
// El constructor sin argumentos es protected porque solo lo necesita Hibernate,
// no debería usarse desde el resto del código.
@Entity
@Table(name = "pistas")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PistaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // unique porque data.sql usa INSERT IGNORE apoyándose en esto para no duplicar
    // las pistas de partida cada vez que arranca la aplicación.
    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private boolean activa;
}
