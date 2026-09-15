package com.pgolmeda.padelbooking.domain.model;

import java.time.LocalDateTime;

// Objeto de valor: un hueco libre de tiempo. No tiene id ni ciclo de vida propio,
// se calcula al vuelo, no se persiste.
public record FranjaHoraria(LocalDateTime inicio, LocalDateTime fin) {
}
