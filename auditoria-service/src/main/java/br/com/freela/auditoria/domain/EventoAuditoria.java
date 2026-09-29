package br.com.freela.auditoria.domain;

import java.time.Instant;
import java.util.UUID;

public class EventoAuditoria {

    private UUID id;
    private UUID eventId;
    private UUID aggregateId;
    private String eventType;
    private String correlationId;
    private String payload;
    private Instant recebidoEm;

    public EventoAuditoria() {
    }

    public EventoAuditoria(UUID id, UUID eventId, UUID aggregateId, String eventType, String correlationId, String payload, Instant recebidoEm) {
        this.id = id;
        this.eventId = eventId;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.correlationId = correlationId;
        this.payload = payload;
        this.recebidoEm = recebidoEm;
    }

    public static EventoAuditoria criar(UUID eventId, UUID aggregateId, String eventType, String correlationId, String payload, Instant recebidoEm) {
        if (eventId == null || aggregateId == null) throw new IllegalArgumentException("Evento e Aggregate são obrigatórios");
        if (eventType == null || eventType.isBlank()) throw new IllegalArgumentException("Tipo é obrigatório");
        return new EventoAuditoria(UUID.randomUUID(), eventId, aggregateId, eventType, correlationId, payload, recebidoEm);
    }

    public static EventoAuditoria restaurar(UUID id, UUID eventId, UUID aggregateId, String eventType, String correlationId, String payload, Instant recebidoEm) {
        return new EventoAuditoria(id, eventId, aggregateId, eventType, correlationId, payload, recebidoEm);
    }

    public UUID getId() {
        return id;
    }
    public UUID getEventId() {
        return eventId;
    }
    public UUID getAggregateId() {
        return aggregateId;
    }
    public String getEventType() {
        return eventType;
    }
    public String getCorrelationId() {
        return correlationId;
    }
    public String getPayload() {
        return payload;
    }
    public Instant getRecebidoEm() {
        return recebidoEm;
    }
}
