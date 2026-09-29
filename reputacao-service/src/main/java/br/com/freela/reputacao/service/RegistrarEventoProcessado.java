package br.com.freela.reputacao.service;

import br.com.freela.reputacao.domain.evento.EventoProcessado;
import br.com.freela.reputacao.infrastructure.eventos.EventoProcessadoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RegistrarEventoProcessado {

    private final EventoProcessadoRepository eventoProcessadoRepository;

    public RegistrarEventoProcessado(EventoProcessadoRepository eventoProcessadoRepository) {
        this.eventoProcessadoRepository = eventoProcessadoRepository;
    }

    public void registrarEvento(UUID idEvento, Instant processadoEm){
        EventoProcessado eventoProcessado = EventoProcessado.criar(idEvento, processadoEm);
        eventoProcessadoRepository.salvar(eventoProcessado);
    }

}
