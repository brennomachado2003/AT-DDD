package br.com.freela.auditoria.service;

import br.com.freela.auditoria.domain.EventoAuditoria;
import br.com.freela.auditoria.infrastructure.EventoAuditoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListarEventoAuditoriaService {

    private final EventoAuditoriaRepository eventoAuditoriaRepository;

    public ListarEventoAuditoriaService(EventoAuditoriaRepository eventoAuditoriaRepository){
        this.eventoAuditoriaRepository = eventoAuditoriaRepository;
    }

    public List<EventoAuditoria> listarEventosAuditoria(){
        return eventoAuditoriaRepository.listarEventoAuditoria();
    }
}
