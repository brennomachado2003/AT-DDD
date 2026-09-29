package br.com.freela.reputacao.infrastructure.eventos;

import br.com.freela.reputacao.domain.evento.EventoProcessado;
import br.com.freela.reputacao.domain.reputacao.ReputacaoFreelancer;
import br.com.freela.reputacao.infrastructure.reputacao.ReputacaoFreelancerEntity;
import br.com.freela.reputacao.infrastructure.reputacao.ReputacaoFreelancerJPARepository;
import br.com.freela.reputacao.infrastructure.reputacao.ReputacaoFreelancerMapper;
import br.com.freela.reputacao.infrastructure.reputacao.ReputacaoFreelancerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public class EventoProcessadoRepositoryJpaAdapter implements EventoProcessadoRepository {

    private final EventoProcessadoJPARepository jpa;

    public EventoProcessadoRepositoryJpaAdapter(EventoProcessadoJPARepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public EventoProcessado salvar(EventoProcessado eventoProcessado) {
        EventoProcessadoEntity eventoProcessadoEntity = EventoProcessadoMapper.toEntity(eventoProcessado);
        EventoProcessadoEntity savo = jpa.save(eventoProcessadoEntity);
        return EventoProcessadoMapper.toDomain(savo);
    }

    @Override
    public boolean verificarSeEventoExiste(UUID idEvento){
       return jpa.existsByEventId(idEvento);
    }

}
