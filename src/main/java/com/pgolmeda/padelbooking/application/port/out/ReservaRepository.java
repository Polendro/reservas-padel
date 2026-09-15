package com.pgolmeda.padelbooking.application.port.out;

import com.pgolmeda.padelbooking.domain.model.Reserva;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Puerto de salida: lo que la aplicación necesita del exterior para persistir reservas.
 * La implementación real (JPA, en memoria para tests, lo que sea) vive en infrastructure/.
 */
public interface ReservaRepository {

    Reserva guardar(Reserva reserva);

    boolean existeSolapamiento(Long pistaId, LocalDateTime inicio, LocalDateTime fin);

    Optional<Reserva> buscarPorId(Long id);
}
