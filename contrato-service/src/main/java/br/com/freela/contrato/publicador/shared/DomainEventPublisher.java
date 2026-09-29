package br.com.freela.contrato.publicador.shared;

import java.util.Collection;

public interface DomainEventPublisher {
    void publicar(Collection<DomainEvent> events);
    void enviar(DomainEvent evento);
}
