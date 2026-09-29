package br.com.freela.contrato.infrastructure.persistence;
import br.com.freela.contrato.domain.model.Contrato;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface ContratoRepository {
    Contrato salvar(Contrato contrato);
    Optional<Contrato> buscarPorId(UUID id);
    List<Contrato> listar();
}
