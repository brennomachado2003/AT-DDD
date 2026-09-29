package br.com.freela.notificacao.service;

import br.com.freela.notificacao.infrastructure.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificarExistenciaEventoService {

    private final NotificacaoRepository notificacaoRepository;

    public VerificarExistenciaEventoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public boolean existePorEventoId(UUID idEvento){
        return notificacaoRepository.verificarExistenciaEvento(idEvento);
    }
}
