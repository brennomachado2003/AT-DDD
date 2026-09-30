package br.com.freela.contrato.publicador.shared;


import br.com.freela.contrato.publicador.event.ContratoCancelado;
import br.com.freela.contrato.publicador.event.ContratoConcluido;
import br.com.freela.contrato.publicador.event.ContratoCriado;
import br.com.freela.contrato.publicador.event.ContratoEntregue;
import br.com.freela.contrato.publicador.kafka.ContratoKafka;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class KafkaDomainEventsPublisher implements DomainEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${topics.contrato-criado}")
    private String contratoCriadoTopic;

    @Value("${topics.contrato-entregue}")
    private String contratoEntregueTopic;

    @Value("${topics.contrato-cancelado}")
    private String contratoCanceladoTopic;

    @Value("${topics.contrato-concluido}")
    private String contratoConcluidoTopic;

    public KafkaDomainEventsPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publicar(Collection<DomainEvent> events) {
        events.forEach(this::enviar);
    }

    @Override
    public void enviar(DomainEvent evento) {
        if (evento instanceof ContratoCriado contrato) {
            var mensagem = new ContratoKafka(
                    contrato.eventId(),
                    contrato.occurredAt(),
                    contrato.contratoId(),
                    contrato.clienteId(),
                    contrato.freelancerId(),
                    contrato.titulo(),
                    contrato.valor(),
                    contrato.eventType()
            );
            kafkaTemplate.send(contratoCriadoTopic, contrato.contratoId().toString(), mensagem);
        }
        if (evento instanceof ContratoEntregue contrato) {
            var mensagem = new ContratoKafka(
                    contrato.eventId(),
                    contrato.occurredAt(),
                    contrato.contratoId(),
                    contrato.clienteId(),
                    contrato.freelancerId(),
                    contrato.titulo(),
                    contrato.valor(),
                    contrato.eventType()
            );
            kafkaTemplate.send(contratoEntregueTopic, contrato.contratoId().toString(), mensagem);
        }
        if (evento instanceof ContratoCancelado contrato) {

            var mensagem = new ContratoKafka(
                    contrato.eventId(),
                    contrato.occurredAt(),
                    contrato.contratoId(),
                    contrato.clienteId(),
                    contrato.freelancerId(),
                    contrato.titulo(),
                    contrato.valor(),
                    contrato.eventType()
            );
            kafkaTemplate.send(contratoCanceladoTopic, contrato.contratoId().toString(), mensagem);
        }
        if (evento instanceof ContratoConcluido contrato) {
            var mensagem = new ContratoKafka(
                    contrato.eventId(),
                    contrato.occurredAt(),
                    contrato.contratoId(),
                    contrato.clienteId(),
                    contrato.freelancerId(),
                    contrato.titulo(),
                    contrato.valor(),
                    contrato.eventType()
            );
            kafkaTemplate.send(contratoConcluidoTopic, contrato.contratoId().toString(), mensagem);
        }
    }
}