package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioJpaEntity, Long> {

    Optional<UsuarioJpaEntity> findByEmail(String email);
}
