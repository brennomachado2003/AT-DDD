package br.com.freela.reputacao.infrastructure.eventos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventoProcessadoJPARepository extends JpaRepository<EventoProcessadoEntity, UUID> {
    boolean existsByEventId(UUID eventId);
}
