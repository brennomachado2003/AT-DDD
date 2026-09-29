package br.com.freela.contrato.service;

import br.com.freela.contrato.Erros.ContratoNaoEncontradoException;
import br.com.freela.contrato.domain.model.Contrato;
import br.com.freela.contrato.infrastructure.persistence.ContratoRepository;
import br.com.freela.contrato.publicador.shared.DomainEventPublisher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CancelarService {

    private final ContratoRepository repository;
    private final DomainEventPublisher domainEventPublisher;

    public CancelarService(ContratoRepository repository,  DomainEventPublisher domainEventPublisher) {
        this.repository = repository;
        this.domainEventPublisher = domainEventPublisher;
    }

    public void cancelarContrato(UUID id) {
        Contrato contrato = repository.buscarPorId(id).orElseThrow(() -> new ContratoNaoEncontradoException(id));
        contrato.cancelar();
        repository.salvar(contrato);
        domainEventPublisher.publicar(contrato.getEvents());
        contrato.limparEvents();
    }
}
