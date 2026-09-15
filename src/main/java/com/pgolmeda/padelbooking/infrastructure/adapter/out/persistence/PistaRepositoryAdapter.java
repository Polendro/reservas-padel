package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence;

import com.pgolmeda.padelbooking.application.port.out.PistaRepository;
import com.pgolmeda.padelbooking.domain.model.Pista;
import com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence.mapper.PistaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PistaRepositoryAdapter implements PistaRepository {

    private final SpringDataPistaRepository springDataPistaRepository;

    public PistaRepositoryAdapter(SpringDataPistaRepository springDataPistaRepository) {
        this.springDataPistaRepository = springDataPistaRepository;
    }

    @Override
    public Optional<Pista> buscarPorId(Long id) {
        return springDataPistaRepository.findById(id).map(PistaMapper::aDominio);
    }

    @Override
    public List<Pista> listarActivas() {
        return springDataPistaRepository.findByActivaTrue().stream()
                .map(PistaMapper::aDominio)
                .toList();
    }
}
