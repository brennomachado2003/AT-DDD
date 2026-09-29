package br.com.freela.contrato.publicador.kafka;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ContratoKafka(
        UUID eventId,
        Instant occurredAt,
        UUID contratoId,
        UUID clienteId,
        UUID freelancerId,
        String titulo,
        BigDecimal valor,
        String tipo) {}
