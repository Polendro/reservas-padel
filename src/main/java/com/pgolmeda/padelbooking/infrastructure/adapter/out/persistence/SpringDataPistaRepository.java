package com.pgolmeda.padelbooking.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPistaRepository extends JpaRepository<PistaJpaEntity, Long> {
}
