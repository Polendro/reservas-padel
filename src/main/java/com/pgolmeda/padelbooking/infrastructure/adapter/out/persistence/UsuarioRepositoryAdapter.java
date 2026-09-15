package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence;

import com.pgolmeda.padelbooking.application.port.out.UsuarioRepository;
import com.pgolmeda.padelbooking.domain.model.Usuario;
import com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence.mapper.UsuarioMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final SpringDataUsuarioRepository springDataUsuarioRepository;

    public UsuarioRepositoryAdapter(SpringDataUsuarioRepository springDataUsuarioRepository) {
        this.springDataUsuarioRepository = springDataUsuarioRepository;
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioJpaEntity guardado = springDataUsuarioRepository.save(UsuarioMapper.aEntidad(usuario));
        return UsuarioMapper.aDominio(guardado);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return springDataUsuarioRepository.findByEmail(email).map(UsuarioMapper::aDominio);
    }
}
