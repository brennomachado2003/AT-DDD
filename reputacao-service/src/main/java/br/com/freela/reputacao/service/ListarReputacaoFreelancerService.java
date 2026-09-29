package br.com.freela.reputacao.service;

import br.com.freela.reputacao.domain.reputacao.ReputacaoFreelancer;
import br.com.freela.reputacao.infrastructure.reputacao.ReputacaoFreelancerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListarReputacaoFreelancerService {

    private static final Logger log= LoggerFactory.getLogger(ListarReputacaoFreelancerService.class);
    private final ReputacaoFreelancerRepository repository;

    public ListarReputacaoFreelancerService(ReputacaoFreelancerRepository repository){
        this.repository = repository;
    }

    public List<ReputacaoFreelancer> listarReputacaoFreelancer(){
        return repository.listarReputacaoFreelancer();
    }
}
