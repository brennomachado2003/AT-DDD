package br.com.freela.auditoria.infrastructure;

import br.com.freela.auditoria.domain.EventoAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventoAuditoriaRepository{

    EventoAuditoria salvar(EventoAuditoria eventoAuditoria);
    List<EventoAuditoria> listarEventoAuditoria();
    boolean existePorEventId(UUID idEvento);
}
