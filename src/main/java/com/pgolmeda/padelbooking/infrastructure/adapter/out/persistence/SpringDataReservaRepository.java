package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

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
}
