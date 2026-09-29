package br.com.freela.reputacao.service;

import br.com.freela.reputacao.infrastructure.eventos.EventoProcessadoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificarSeEventoExisteService {

    private final EventoProcessadoRepository eventoProcessadoRepository;

    public VerificarSeEventoExisteService(EventoProcessadoRepository eventoProcessadoRepository) {
        this.eventoProcessadoRepository = eventoProcessadoRepository;
    }

    public boolean verificarSeEventoExiste(UUID idEvento){
        return eventoProcessadoRepository.verificarSeEventoExiste(idEvento);
    }
}
