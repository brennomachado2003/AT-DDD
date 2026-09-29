package br.com.freela.contrato.infrastructure.persistence;

import br.com.freela.contrato.domain.model.Contrato;
import br.com.freela.contrato.dto.CriarContratoCommand;
import br.com.freela.contrato.dto.CriarContratoRequest;

public class ContratoMapper {

    public static ContratoJpaEntity toEntity(Contrato contrato) {
        return new ContratoJpaEntity(
                contrato.id(),
                contrato.clienteId(),
                contrato.freelancerId(),
                contrato.titulo(),
                contrato.valor(),
                contrato.status(),
                contrato.criadoEm()
        );
    }

    public static Contrato toDomain(ContratoJpaEntity e) {
        return Contrato.restaurar(e.getId(), e.getClienteId(), e.getFreelancerId(), e.getTitulo(), e.getValor(), e.getStatus(), e.getCriadoEm());
    }

    public static CriarContratoCommand criarContratoRequestTocriarContratoCommand(CriarContratoRequest contrato) {
        return new CriarContratoCommand(contrato.clienteId(), contrato.freelancerId(), contrato.titulo(), contrato.valor());
    }
}
