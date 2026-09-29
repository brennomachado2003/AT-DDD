package br.com.freela.notificacao.infrastructure;

import br.com.freela.notificacao.domain.Notificacao;

public class NotificacaoMapper {

    public static NotificacaoEntity toEntity(Notificacao notificacao) {
        return new NotificacaoEntity(
                notificacao.getEventId(),
                notificacao.getContratoId(),
                notificacao.getDestinatarioId(),
                notificacao.getTipo(),
                notificacao.getMenssagem()
        );
    }

    public static Notificacao toDomain(NotificacaoEntity notificacaoEntity) {
        return Notificacao.restaurar(
                notificacaoEntity.getId(),
                notificacaoEntity.getContratoId(),
                notificacaoEntity.getDestinatarioId(),
                notificacaoEntity.getTipo(),
                notificacaoEntity.getMensagem(),
                notificacaoEntity.getCriadaEm()
        );
    }
}
