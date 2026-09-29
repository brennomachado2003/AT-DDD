package br.com.freela.auditoria.infrastructure;

import br.com.freela.auditoria.domain.EventoAuditoria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class EventoAuditoriaRepositoryJpaAdapter implements EventoAuditoriaRepository {

    private final EventoAuditoriaJPARepository jpa;

    public EventoAuditoriaRepositoryJpaAdapter(EventoAuditoriaJPARepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public EventoAuditoria salvar(EventoAuditoria eventoAuditoria) {
        EventoAuditoriaEntity eventoAuditoriaEntity = EventoAuditoriaMapper.toEntity(eventoAuditoria);
        EventoAuditoriaEntity savo = jpa.save(eventoAuditoriaEntity);
        return EventoAuditoriaMapper.toDomain(savo);
    }

    @Override
    public List<EventoAuditoria> listarEventoAuditoria(){
        return jpa.findAll().stream().map(EventoAuditoriaMapper::toDomain).toList();
    }

    @Override
    public boolean existePorEventId(UUID idEvento){
        return jpa.existsByEventId(idEvento);
    }
}
