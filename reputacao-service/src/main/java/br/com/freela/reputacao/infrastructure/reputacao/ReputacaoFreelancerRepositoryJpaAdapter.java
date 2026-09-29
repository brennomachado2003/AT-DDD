package br.com.freela.reputacao.infrastructure.reputacao;

import br.com.freela.reputacao.domain.reputacao.ReputacaoFreelancer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public class ReputacaoFreelancerRepositoryJpaAdapter implements ReputacaoFreelancerRepository {

    private final ReputacaoFreelancerJPARepository jpa;

    public ReputacaoFreelancerRepositoryJpaAdapter(ReputacaoFreelancerJPARepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public ReputacaoFreelancer salvar(ReputacaoFreelancer reputacaoFreelancer) {
        ReputacaoFreelancerEntity reputacaoFreelancerEntity = ReputacaoFreelancerMapper.toEntity(reputacaoFreelancer);
        ReputacaoFreelancerEntity savo = jpa.save(reputacaoFreelancerEntity);
        return ReputacaoFreelancerMapper.toDomain(savo);
    }

    @Override
    public List<ReputacaoFreelancer> listarReputacaoFreelancer(){
        List<ReputacaoFreelancerEntity> reputacaoFreelancerEntityList = jpa.findAll();
        return reputacaoFreelancerEntityList.stream().map(ReputacaoFreelancerMapper::toDomain).toList();
    }

    @Override
    public ReputacaoFreelancer buscarPorId(UUID id){
        ReputacaoFreelancerEntity reputacaoFreelancerEntity = jpa.findById(id).orElseGet(()-> new ReputacaoFreelancerEntity(id));
        return ReputacaoFreelancerMapper.toDomain(reputacaoFreelancerEntity);
    }

}
