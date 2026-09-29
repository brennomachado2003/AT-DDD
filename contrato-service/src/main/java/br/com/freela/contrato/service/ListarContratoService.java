package br.com.freela.contrato.service;

import br.com.freela.contrato.domain.model.Contrato;
import br.com.freela.contrato.infrastructure.persistence.ContratoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListarContratoService {

    private static final Logger log = LoggerFactory.getLogger(ListarContratoService.class);
    private final ContratoRepository repository;

    public ListarContratoService(ContratoRepository repository) { this.repository = repository; }

    @Transactional(readOnly = true)
    public List<Contrato> listar() {
        log.info("contrato.listagem.inicio");
        List<Contrato> contratos = repository.listar();
        log.info("contrato.listagem.sucesso quantidade={}", contratos.size());
        return contratos;
    }
}
