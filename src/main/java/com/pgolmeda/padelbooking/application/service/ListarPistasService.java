package com.pgolmeda.padelbooking.application.service;

import com.pgolmeda.padelbooking.application.port.in.ListarPistasUseCase;
import com.pgolmeda.padelbooking.application.port.out.PistaRepository;
import com.pgolmeda.padelbooking.domain.model.Pista;
import org.springframework.stereotype.Service;

import java.util.List;

// El servicio más simple del proyecto: no hay regla de negocio que aplicar,
// solo delega. Aun así lleva su puerto de entrada, por consistencia con el resto.
@Service
public class ListarPistasService implements ListarPistasUseCase {

    private final PistaRepository pistaRepository;

    public ListarPistasService(PistaRepository pistaRepository) {
        this.pistaRepository = pistaRepository;
    }

    @Override
    public List<Pista> listar() {
        return pistaRepository.listarActivas();
    }
}
