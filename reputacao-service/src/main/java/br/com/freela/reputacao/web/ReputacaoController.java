package br.com.freela.reputacao.web;
import br.com.freela.reputacao.domain.reputacao.ReputacaoFreelancer;
import br.com.freela.reputacao.service.ListarReputacaoFreelancerService;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/reputacoes")
public class ReputacaoController {


  private static final Logger log=LoggerFactory.getLogger(ReputacaoController.class);
  private final ListarReputacaoFreelancerService listarReputacaoFreelancerService;

  ReputacaoController(ListarReputacaoFreelancerService listarReputacaoFreelancerService){
   this.listarReputacaoFreelancerService = listarReputacaoFreelancerService;
  }

  @GetMapping public List<ReputacaoFreelancer> listar(@RequestHeader(value="X-Correlation-Id",required=false) String correlationId){
    log.info("http.reputacao.listar correlationId={}",correlationId);
    return listarReputacaoFreelancerService.listarReputacaoFreelancer();
  }
}
