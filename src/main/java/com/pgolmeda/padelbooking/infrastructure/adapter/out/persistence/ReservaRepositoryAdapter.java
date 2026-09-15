package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence;

import com.pgolmeda.padelbooking.application.port.out.ReservaRepository;
import com.pgolmeda.padelbooking.domain.model.Reserva;
import com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence.mapper.ReservaMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
}
