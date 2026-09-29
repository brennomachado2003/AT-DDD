package br.com.freela.contrato.publicador.event;

import br.com.freela.contrato.domain.model.Contrato;
import br.com.freela.contrato.publicador.shared.DomainEvent;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ContratoEntregue(
        UUID eventId,
        Instant occurredAt,
        UUID contratoId,
        UUID clienteId,
        UUID freelancerId,
        String status,
        String titulo,
        BigDecimal valor
)
        implements DomainEvent {

    public static ContratoEntregue novo(Contrato c) {
        return new ContratoEntregue(
                UUID.randomUUID(),
                Instant.now(),
                c.id(),
                c.clienteId(),
                c.freelancerId(),
                c.status().toString(),
                c.titulo(),
                c.valor()
        );
    }
    @Override
    public String eventType() { return "ContratoEntregue"; }
}
