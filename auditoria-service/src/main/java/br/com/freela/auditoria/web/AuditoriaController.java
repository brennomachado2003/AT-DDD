package br.com.freela.auditoria.web;

import br.com.freela.auditoria.domain.EventoAuditoria;
import br.com.freela.auditoria.service.ListarEventoAuditoriaService;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

 private static final Logger log = LoggerFactory.getLogger(AuditoriaController.class);
 private final ListarEventoAuditoriaService listarEventoAuditoriaService;

 AuditoriaController(ListarEventoAuditoriaService listarEventoAuditoriaService) {
  this.listarEventoAuditoriaService = listarEventoAuditoriaService;
 }

 @GetMapping
 public List<EventoAuditoria> listar(@RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
  log.info("http.auditoria.listar correlationId={}", correlationId);
  return listarEventoAuditoriaService.listarEventosAuditoria();
 }

}
