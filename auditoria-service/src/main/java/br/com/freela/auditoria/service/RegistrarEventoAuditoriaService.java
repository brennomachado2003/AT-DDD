package br.com.freela.auditoria.service;
import br.com.freela.auditoria.domain.EventoAuditoria;
import br.com.freela.auditoria.infrastructure.EventoAuditoriaRepository;
import org.slf4j.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;
@Service
public class RegistrarEventoAuditoriaService {

  private static final Logger log=LoggerFactory.getLogger(RegistrarEventoAuditoriaService.class);
  private final EventoAuditoriaRepository eventoAuditoriaRepository;

  public RegistrarEventoAuditoriaService(EventoAuditoriaRepository eventoAuditoriaRepository){
   this.eventoAuditoriaRepository = eventoAuditoriaRepository;
  }

  @Transactional
  public void registrar(UUID eventId, UUID aggregateId, String eventType, String correlationId, String payload, Instant recebidoEm) {
      log.info("auditoria.registro.inicio eventId={} aggregateId={} eventType={} correlationId={}",eventId,aggregateId,eventType,correlationId);
      EventoAuditoria eventoAuditoria = EventoAuditoria.criar(eventId, aggregateId, eventType, correlationId, payload, recebidoEm);
      EventoAuditoria salvo = eventoAuditoriaRepository.salvar(eventoAuditoria);
      log.info("auditoria.registro.sucesso auditoriaId={} eventId={} aggregateId={} eventType={}",salvo.getId(),eventId,aggregateId,eventType);
  }
}
