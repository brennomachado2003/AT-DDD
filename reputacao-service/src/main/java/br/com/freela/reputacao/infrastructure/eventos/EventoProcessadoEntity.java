package br.com.freela.reputacao.infrastructure.eventos;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "eventos_processados",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_evento_processado_event_id",
                        columnNames = "event_id"
                )
        }
)
public class EventoProcessadoEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID eventId;

    private Instant processadoEm;

    protected EventoProcessadoEntity() {}

    public EventoProcessadoEntity(UUID id, UUID eventId,  Instant processadoEm) {
        this.id = id;
        this.eventId = eventId;
        this.processadoEm = processadoEm;
    }

    public UUID getId() {
        return id;
    }

    public UUID getEventId() {
        return eventId;
    }

    public Instant getProcessadoEm() {
        return processadoEm;
    }
}
