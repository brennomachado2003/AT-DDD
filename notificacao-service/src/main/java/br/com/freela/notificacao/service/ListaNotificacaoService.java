package br.com.freela.notificacao.service;

import br.com.freela.notificacao.domain.Notificacao;
import br.com.freela.notificacao.infrastructure.NotificacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaNotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private static final Logger log= LoggerFactory.getLogger(ListaNotificacaoService.class);


    public ListaNotificacaoService(NotificacaoRepository notificacaoRepository){
        this.notificacaoRepository = notificacaoRepository;
    }

    public List<Notificacao> listarNotificacao(String correlationId){
        log.info("http.notificacao.listar correlationId={}",correlationId);
        return notificacaoRepository.listarNotificacao();
    }
}
