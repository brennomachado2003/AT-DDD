package br.com.freela.notificacao.web;
import br.com.freela.notificacao.domain.Notificacao;
import br.com.freela.notificacao.service.ListaNotificacaoService;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

  private final ListaNotificacaoService listaNotificacaoService;

  NotificacaoController(ListaNotificacaoService listaNotificacaoService){
    this.listaNotificacaoService = listaNotificacaoService;
  }

  @GetMapping
  public List<Notificacao> listar(@RequestHeader(value="X-Correlation-Id",required=false) String correlationId){
    return listaNotificacaoService.listarNotificacao(correlationId);
  }

}
