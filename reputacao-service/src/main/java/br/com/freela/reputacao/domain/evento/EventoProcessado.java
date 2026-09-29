package br.com.freela.reputacao.domain.evento;

import br.com.freela.reputacao.domain.reputacao.ReputacaoFreelancer;

import java.time.Instant;
import java.util.UUID;

public class EventoProcessado {

    private UUID id;
    private UUID eventId;
    private Instant processadoEm;

    public EventoProcessado(UUID eventId, Instant processadoEm) {
        this.id = UUID.randomUUID();
        this.eventId = eventId;
        this.processadoEm = processadoEm;
    }

    public EventoProcessado(UUID id, UUID eventId, Instant processadoEm) {
        this.id = id;
        this.eventId = eventId;
        this.processadoEm = processadoEm;
    }

    public static EventoProcessado criar(UUID eventId, Instant processadoEm) {
        return new EventoProcessado(eventId, processadoEm);
    }

    public static EventoProcessado restaurar(UUID id, UUID eventId, Instant processadoEm) {
        return new EventoProcessado(id, eventId,  processadoEm);
    }

    public UUID getEventId() {
        return eventId;
    }

    public Instant getProcessadoEm() {
        return processadoEm;
    }

    public UUID getId() {
        return id;
    }
}
