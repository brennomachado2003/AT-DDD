package br.com.freela.auditoria.service;

import br.com.freela.auditoria.infrastructure.EventoAuditoriaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificarExistenciaEventoService {

    private final EventoAuditoriaRepository eventoAuditoriaRepository;

    public VerificarExistenciaEventoService(EventoAuditoriaRepository eventoAuditoriaRepository) {
        this.eventoAuditoriaRepository = eventoAuditoriaRepository;
    }

    public boolean existePorEventoId(UUID idEvento){
        return eventoAuditoriaRepository.existePorEventId(idEvento);
    }
}
