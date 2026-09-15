package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence;

import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.domain.model.EstadoReserva;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence.mapper.ReservaMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Implementación de ReservaRepository con Spring Data + MySQL.
@Component
public class ReservaRepositoryAdapter implements ReservaRepository {

    private final SpringDataReservaRepository springDataReservaRepository;

    public ReservaRepositoryAdapter(SpringDataReservaRepository springDataReservaRepository) {
        this.springDataReservaRepository = springDataReservaRepository;
    }

    @Override
    public Reserva guardar(Reserva reserva) {
        ReservaJpaEntity guardada = springDataReservaRepository.save(ReservaMapper.aEntidad(reserva));
        return ReservaMapper.aDominio(guardada);
    }

    @Override
    public boolean existeSolapamiento(Long pistaId, LocalDateTime inicio, LocalDateTime fin) {
        return springDataReservaRepository.existeSolapamiento(pistaId, inicio, fin);
    }

    @Override
    public Optional<Reserva> buscarPorId(Long id) {
        return springDataReservaRepository.findById(id).map(ReservaMapper::aDominio);
    }

    @Override
    public List<Reserva> buscarConfirmadasPorPistaYFecha(Long pistaId, LocalDate fecha) {
        LocalDateTime inicioDelDia = fecha.atStartOfDay();
        LocalDateTime finDelDia = fecha.plusDays(1).atStartOfDay();

        return springDataReservaRepository
                .findByPistaIdAndEstadoAndInicioBetween(pistaId, EstadoReserva.CONFIRMADA, inicioDelDia, finDelDia)
                .stream()
                .map(ReservaMapper::aDominio)
                .toList();
    }

    @Override
    public List<Reserva> buscarPorUsuarioId(Long usuarioId) {
        return springDataReservaRepository.findByUsuarioIdOrderByInicioDesc(usuarioId)
                .stream()
                .map(ReservaMapper::aDominio)
                .toList();
    }
}
