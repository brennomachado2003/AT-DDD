package br.com.freela.reputacao.infrastructure.reputacao;

import br.com.freela.reputacao.domain.reputacao.ReputacaoFreelancer;

public class ReputacaoFreelancerMapper {

    public static ReputacaoFreelancerEntity toEntity(ReputacaoFreelancer reputacaoFreelancer) {
        return new ReputacaoFreelancerEntity(
                reputacaoFreelancer.getFreelancerId(),
                reputacaoFreelancer.getContratosConcluidos(),
                reputacaoFreelancer.getValorTotal()
        );
    }

    public static ReputacaoFreelancer toDomain(ReputacaoFreelancerEntity reputacaoFreelancerEntity) {
        return ReputacaoFreelancer.restaurar(
                reputacaoFreelancerEntity.getFreelancerId(),
                reputacaoFreelancerEntity.getContratosConcluidos(),
                reputacaoFreelancerEntity.getValorTotal()
        );
    }
}
