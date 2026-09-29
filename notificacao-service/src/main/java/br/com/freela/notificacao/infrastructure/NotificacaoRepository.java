package br.com.freela.notificacao.infrastructure;

import br.com.freela.notificacao.domain.Notificacao;

import java.util.List;
import java.util.UUID;

public interface NotificacaoRepository{

    Notificacao salvar(Notificacao notificacao);
    List<Notificacao> listarNotificacao();
    boolean verificarExistenciaEvento(UUID eventoId);
}
