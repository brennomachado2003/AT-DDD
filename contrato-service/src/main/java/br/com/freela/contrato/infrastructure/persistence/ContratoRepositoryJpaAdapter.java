package br.com.freela.contrato.infrastructure.persistence;

import br.com.freela.contrato.domain.model.Contrato;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ContratoRepositoryJpaAdapter implements ContratoRepository {
    private static final Logger log = LoggerFactory.getLogger(ContratoRepositoryJpaAdapter.class);
    private final SpringDataContratoRepository jpa;
    public ContratoRepositoryJpaAdapter(SpringDataContratoRepository jpa) { this.jpa = jpa; }

    @Override public Contrato salvar(Contrato c) {
        log.info("contrato.persistence.save.inicio contratoId={} status={}", c.id(), c.status());
        ContratoJpaEntity entity = ContratoMapper.toEntity(c);
        ContratoJpaEntity saved = jpa.save(entity);
        log.info("contrato.persistence.save.sucesso contratoId={} status={}", saved.getId(), saved.getStatus());
        return ContratoMapper.toDomain(saved);
    }
    @Override public Optional<Contrato> buscarPorId(UUID id) {
        log.info("contrato.persistence.findById contratoId={}", id);
        return jpa.findById(id).map(ContratoMapper::toDomain);
    }
    @Override public List<Contrato> listar() {
        log.info("contrato.persistence.findAll");
        return jpa.findAll().stream().map(ContratoMapper::toDomain).toList();
    }

}
