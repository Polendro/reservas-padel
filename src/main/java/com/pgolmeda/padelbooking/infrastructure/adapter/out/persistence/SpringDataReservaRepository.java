package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence;

import com.pgolmeda.padelbooking.domain.model.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringDataReservaRepository extends JpaRepository<ReservaJpaEntity, Long> {

    // Solo cuenta reservas CONFIRMADAS: una cancelada no debe bloquear el horario.
    @Query("""
            select case when count(r) > 0 then true else false end
            from ReservaJpaEntity r
            where r.pistaId = :pistaId
              and r.estado = com.pgolmeda.padelbooking.domain.model.EstadoReserva.CONFIRMADA
              and r.inicio < :fin
              and :inicio < r.fin
            """)
    boolean existeSolapamiento(@Param("pistaId") Long pistaId,
                                @Param("inicio") LocalDateTime inicio,
                                @Param("fin") LocalDateTime fin);

    // Derived query method: Spring Data genera la consulta a partir del nombre,
    // no hace falta @Query cuando es así de directo.
    List<ReservaJpaEntity> findByPistaIdAndEstadoAndInicioBetween(
            Long pistaId, EstadoReserva estado, LocalDateTime desde, LocalDateTime hasta);
}
