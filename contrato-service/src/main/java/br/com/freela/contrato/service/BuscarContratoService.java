package br.com.freela.contrato.service;

import br.com.freela.contrato.domain.model.Contrato;
import br.com.freela.contrato.infrastructure.persistence.ContratoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class BuscarContratoService {

    private static final Logger log = LoggerFactory.getLogger(BuscarContratoService.class);
    private final ContratoRepository repository;

    public BuscarContratoService(ContratoRepository repository) { this.repository = repository; }

    @Transactional(readOnly = true)
    public Contrato buscar(UUID id) {
        log.info("contrato.busca.inicio contratoId={}", id);
        Contrato contrato = repository.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado: " + id));
        log.info("contrato.busca.sucesso contratoId={} status={}", id, contrato.status());
        return contrato;
    }
}
