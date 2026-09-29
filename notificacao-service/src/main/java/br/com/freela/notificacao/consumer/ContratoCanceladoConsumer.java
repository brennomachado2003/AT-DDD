package br.com.freela.notificacao.consumer;

import br.com.freela.notificacao.consumer.events.ContratoKafka;
import br.com.freela.notificacao.service.RegistrarNotificacaoService;
import br.com.freela.notificacao.service.VerificarExistenciaEventoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class ContratoCanceladoConsumer {

    private static final Logger log= LoggerFactory.getLogger(ContratoCanceladoConsumer.class);
    private final ObjectMapper objectMapper;
    private final RegistrarNotificacaoService registrarNotificacaoService;
    private final VerificarExistenciaEventoService verificarExistenciaEventoService;

    public ContratoCanceladoConsumer(
            ObjectMapper objectMapper,
            RegistrarNotificacaoService registrarNotificacaoService,
            VerificarExistenciaEventoService verificarExistenciaEventoService
    ) {
        this.objectMapper = objectMapper;
        this.registrarNotificacaoService = registrarNotificacaoService;
        this.verificarExistenciaEventoService = verificarExistenciaEventoService;
    }

    @KafkaListener(topics = "${topics.contrato-cancelado}", groupId = "notificacao")
    public void consumir(String mensagem) {
        try {
            log.info("Consumindo os dados de Contrato cancelado");
            ContratoKafka evento = objectMapper.readValue( mensagem, ContratoKafka.class);

            if (verificarExistenciaEventoService.existePorEventoId(evento.eventId())) {
                log.info("Evento já processado eventId={} - ignorando", evento.eventId());
                return;
            }
            log.info("Evento do Contrato cancelado: {}", evento.eventId());
            log.info("Contrato do Contrato cancelado: {}", evento.contratoId());
            log.info("Destinatário do Contrato cancelado: {}", evento.freelancerId());
            registrarNotificacaoService.registrar(evento.eventId(), evento.contratoId(), evento.freelancerId(), evento.tipo(), "Titulo: " + evento.titulo() + "\nFreelancer: " + evento.freelancerId() + "\nCliente: " + evento.clienteId());
            log.info("Notificacao registrado com sucesso!");
        }
        catch (Exception e) {
            throw new RuntimeException( "Erro ao processar mensagem de endereço cadastrado", e );
        }
    }
}
