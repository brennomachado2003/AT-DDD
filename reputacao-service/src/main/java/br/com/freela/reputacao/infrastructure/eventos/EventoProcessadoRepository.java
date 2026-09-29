package br.com.freela.reputacao.infrastructure.eventos;


import br.com.freela.reputacao.domain.evento.EventoProcessado;
import br.com.freela.reputacao.domain.reputacao.ReputacaoFreelancer;

import java.util.List;
import java.util.UUID;

public interface EventoProcessadoRepository {

    EventoProcessado salvar(EventoProcessado reputacaoFreelancer);
    boolean verificarSeEventoExiste(UUID idEvento);
}
