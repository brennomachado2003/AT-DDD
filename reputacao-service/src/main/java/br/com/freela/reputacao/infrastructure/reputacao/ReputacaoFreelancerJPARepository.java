package br.com.freela.reputacao.infrastructure.reputacao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReputacaoFreelancerJPARepository extends JpaRepository<ReputacaoFreelancerEntity, UUID>{

}
