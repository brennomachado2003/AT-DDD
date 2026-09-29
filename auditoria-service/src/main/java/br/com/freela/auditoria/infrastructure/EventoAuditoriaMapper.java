package br.com.freela.auditoria.infrastructure;

import br.com.freela.auditoria.domain.EventoAuditoria;

import java.time.Instant;
import java.util.UUID;

public class EventoAuditoriaMapper {

    public static EventoAuditoriaEntity toEntity(EventoAuditoria eventoAuditoria) {
        return new EventoAuditoriaEntity(
                eventoAuditoria.getId(),
                eventoAuditoria.getEventId(),
                eventoAuditoria.getAggregateId(),
                eventoAuditoria.getEventType(),
                eventoAuditoria.getCorrelationId(),
                eventoAuditoria.getPayload(),
                eventoAuditoria.getRecebidoEm()
        );
    }

    public static EventoAuditoria toDomain(EventoAuditoriaEntity eventoAuditoriaEntity) {
        return EventoAuditoria.restaurar(
                eventoAuditoriaEntity.getId(),
                eventoAuditoriaEntity.getEventId(),
                eventoAuditoriaEntity.getAggregateId(),
                eventoAuditoriaEntity.getEventType(),
                eventoAuditoriaEntity.getCorrelationId(),
                eventoAuditoriaEntity.getPayload(),
                eventoAuditoriaEntity.getRecebidoEm()
        );
    }
}
