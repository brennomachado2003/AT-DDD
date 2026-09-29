package br.com.freela.auditoria.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ContratoConsumer {

    private final AuditoriaEventProcessor processor;

    public ContratoConsumer(AuditoriaEventProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(
            topics = {
                    "${topics.contrato-criado}",
                    "${topics.contrato-entregue}",
                    "${topics.contrato-cancelado}",
                    "${topics.contrato-concluido}"
            },
            groupId = "auditoria"
    )
    public void consumir(ConsumerRecord<String, String> record) {
        processor.processar(record);
    }
}
