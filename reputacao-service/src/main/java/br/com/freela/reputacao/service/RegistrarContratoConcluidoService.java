package br.com.freela.reputacao.service;

import br.com.freela.reputacao.domain.reputacao.ReputacaoFreelancer;
import br.com.freela.reputacao.infrastructure.reputacao.ReputacaoFreelancerRepository;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class RegistrarContratoConcluidoService {

  private static final Logger log=LoggerFactory.getLogger(RegistrarContratoConcluidoService.class);
  private final ReputacaoFreelancerRepository repository;

  public RegistrarContratoConcluidoService(ReputacaoFreelancerRepository repository){
   this.repository = repository;
  }

   @Transactional
   public void registrarContratoConcluido(UUID contratoId,UUID freelancerId,BigDecimal valor){
     log.info("reputacao.atualizacao.inicio contratoId={} freelancerId={} valor={}",contratoId,freelancerId,valor);
     ReputacaoFreelancer reputacaoFreelancer = repository.buscarPorId(freelancerId);
     reputacaoFreelancer.registrarContrato(valor);
     repository.salvar(reputacaoFreelancer);
     log.info("reputacao.atualizacao.sucesso contratoId={} freelancerId={} contratosConcluidos={} valorTotal={}",contratoId,freelancerId,reputacaoFreelancer.getContratosConcluidos(),reputacaoFreelancer.getValorTotal());
   }

}
