package br.com.freela.auditoria.consumer;

import br.com.freela.auditoria.consumer.events.ContratoKafka;
import br.com.freela.auditoria.service.RegistrarEventoAuditoriaService;
import br.com.freela.auditoria.service.VerificarExistenciaEventoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuditoriaEventProcessor {

    private final ObjectMapper objectMapper;
    private final RegistrarEventoAuditoriaService registrarEventoAuditoriaService;
    private final Logger log = LoggerFactory.getLogger(AuditoriaEventProcessor.class);
    private final VerificarExistenciaEventoService verificarExistenciaEventoService;

    public AuditoriaEventProcessor(
            ObjectMapper objectMapper,
            RegistrarEventoAuditoriaService registrarEventoAuditoriaService,
            VerificarExistenciaEventoService verificarExistenciaEventoService
    ) {
        this.objectMapper = objectMapper;
        this.registrarEventoAuditoriaService = registrarEventoAuditoriaService;
        this.verificarExistenciaEventoService = verificarExistenciaEventoService;
    }

    public void processar(ConsumerRecord<String, String> record) {

        try {
            String mensagem = record.value();
            ContratoKafka evento = objectMapper.readValue(mensagem, ContratoKafka.class);
            UUID eventId = evento.eventId();
            if (verificarExistenciaEventoService.existePorEventoId(eventId)) {
                log.info("Evento já processado eventId={} - ignorando", eventId);
                return;
            }

            String key = record.key();

            registrarEventoAuditoriaService.registrar(
                    evento.eventId(),
                    evento.contratoId(),
                    evento.tipo(),
                    key,
                    mensagem,
                    evento.occurredAt()
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao processar evento de contrato", e
            );
        }
    }
}
