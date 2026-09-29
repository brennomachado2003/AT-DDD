package br.com.freela.reputacao.infrastructure.reputacao;


import br.com.freela.reputacao.domain.reputacao.ReputacaoFreelancer;

import java.util.List;
import java.util.UUID;

public interface ReputacaoFreelancerRepository {

    ReputacaoFreelancer salvar(ReputacaoFreelancer reputacaoFreelancer);
    List<ReputacaoFreelancer> listarReputacaoFreelancer();
    ReputacaoFreelancer buscarPorId(UUID id);
}
