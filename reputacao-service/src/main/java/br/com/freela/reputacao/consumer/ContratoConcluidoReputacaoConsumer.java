package br.com.freela.reputacao.consumer;


import br.com.freela.reputacao.consumer.events.ContratoKafka;
import br.com.freela.reputacao.service.RegistrarContratoConcluidoService;
import br.com.freela.reputacao.service.RegistrarEventoProcessado;
import br.com.freela.reputacao.service.VerificarSeEventoExisteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class ContratoConcluidoReputacaoConsumer {

    private static final Logger log= LoggerFactory.getLogger(ContratoConcluidoReputacaoConsumer.class);
    private final ObjectMapper objectMapper;
    private final RegistrarContratoConcluidoService registrarContratoConcluidoService;
    private final RegistrarEventoProcessado registrarEventoProcessado;
    private final VerificarSeEventoExisteService verificarSeEventoExisteService;

    public ContratoConcluidoReputacaoConsumer(
            ObjectMapper objectMapper,
            RegistrarContratoConcluidoService registrarContratoConcluidoService,
            RegistrarEventoProcessado registrarEventoProcessado,
            VerificarSeEventoExisteService verificarSeEventoExisteService
    ) {
        this.objectMapper = objectMapper;
        this.registrarContratoConcluidoService = registrarContratoConcluidoService;
        this.registrarEventoProcessado = registrarEventoProcessado;
        this.verificarSeEventoExisteService = verificarSeEventoExisteService;
    }

    @KafkaListener(topics = "${topics.contrato-concluido}", groupId = "reputacao")
    public void consumir(String mensagem) {
        try {
            log.info("Consumindo os dados de Contrato concluido reputacao");
            ContratoKafka evento = objectMapper.readValue( mensagem, ContratoKafka.class);
            if (verificarSeEventoExisteService.verificarSeEventoExiste(evento.eventId())) {
                log.info("Evento já processado eventId={} - ignorando", evento.eventId());
                return;
            }
            registrarContratoConcluidoService.registrarContratoConcluido(evento.contratoId(), evento.freelancerId(), evento.valor());
            registrarEventoProcessado.registrarEvento(evento.eventId(), evento.occurredAt());
            log.info("Reputacao atualizada com sucesso!");
        }
        catch (Exception e) {
            throw new RuntimeException( "Erro ao processar mensagem de endereço contrato concluido", e );
        }
    }
}
