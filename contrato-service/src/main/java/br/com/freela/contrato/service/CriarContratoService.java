package br.com.freela.contrato.service;

import br.com.freela.contrato.domain.model.Contrato;
import br.com.freela.contrato.publicador.shared.DomainEvent;
import br.com.freela.contrato.dto.CriarContratoCommand;
import br.com.freela.contrato.infrastructure.persistence.ContratoMapper;
import br.com.freela.contrato.infrastructure.persistence.ContratoRepository;
import br.com.freela.contrato.publicador.shared.DomainEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriarContratoService {

    private static final Logger log = LoggerFactory.getLogger(CriarContratoService.class);
    private final ContratoRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    public CriarContratoService(ContratoRepository repository, DomainEventPublisher domainEventPublisher) {
        this.repository = repository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Transactional
    public Contrato criar(CriarContratoCommand cmd) {
        log.info("contrato.criacao.inicio clienteId={} freelancerId={} titulo={} valor={}", cmd.clienteId(), cmd.freelancerId(), cmd.titulo(), cmd.valor());
        Contrato contrato = Contrato.criar(cmd.clienteId(), cmd.freelancerId(), cmd.titulo(), cmd.valor());
        log.info("contrato.dominio.criado contratoId={} status={} domainEvents={}", contrato.id(), contrato.status(), contrato.domainEvents().size());
        Contrato salvo = repository.salvar(contrato);

        // PONTO DO ASSESSMENT:
        // Os eventos existem no Aggregate, mas ainda NÃO são publicados no Kafka.
        // O aluno deverá implementar a estratégia de publicação/mensagens transacionais.
        for (DomainEvent event : contrato.pullDomainEvents()) {
            domainEventPublisher.enviar(event);
            log.info("contrato.evento.pendente contratoId={} eventId={} eventType={} occurredAt={}", contrato.id(), event.eventId(), event.eventType(), event.occurredAt());
        }
        log.info("contrato.criacao.sucesso contratoId={} clienteId={} freelancerId={} status={}", salvo.id(), salvo.clienteId(), salvo.freelancerId(), salvo.status());
        return salvo;
    }
}
