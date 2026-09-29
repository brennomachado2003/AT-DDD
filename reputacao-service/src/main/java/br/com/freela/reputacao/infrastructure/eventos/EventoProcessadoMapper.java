package br.com.freela.reputacao.infrastructure.eventos;

import br.com.freela.reputacao.domain.evento.EventoProcessado;
import br.com.freela.reputacao.domain.reputacao.ReputacaoFreelancer;
import br.com.freela.reputacao.infrastructure.reputacao.ReputacaoFreelancerEntity;

public class EventoProcessadoMapper {

    public static EventoProcessadoEntity toEntity(EventoProcessado eventoProcessado) {
        return new EventoProcessadoEntity(
                eventoProcessado.getId(),
                eventoProcessado.getEventId(),
                eventoProcessado.getProcessadoEm()
        );
    }

    public static EventoProcessado toDomain(EventoProcessadoEntity eventoProcessadoEntity) {
        return EventoProcessado.restaurar(
                eventoProcessadoEntity.getId(),
                eventoProcessadoEntity.getEventId(),
                eventoProcessadoEntity.getProcessadoEm()
        );
    }
}
