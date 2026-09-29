package br.com.freela.notificacao.service;
import br.com.freela.notificacao.domain.Notificacao;
import br.com.freela.notificacao.infrastructure.NotificacaoRepository;
import org.slf4j.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.UUID;
@Service
public class RegistrarNotificacaoService {

 private static final Logger log=LoggerFactory.getLogger(RegistrarNotificacaoService.class);
 private final NotificacaoRepository notificacaoRepository;

 public RegistrarNotificacaoService(NotificacaoRepository notificacaoRepository){
  this.notificacaoRepository = notificacaoRepository;
 }

 @Transactional
 public void registrar(UUID eventoId,UUID contratoId, UUID destinatarioId, String tipo, String mensagem){
   log.info("notificacao.registro.inicio contratoId={} destinatarioId={} tipo={}",contratoId,destinatarioId,tipo);
   Notificacao notificacao = notificacaoRepository.salvar(Notificacao.criar(eventoId, contratoId, destinatarioId, tipo, mensagem));
   log.info("notificacao.registro.sucesso notificacaoId={} contratoId={} destinatarioId={}",notificacao.getId(),contratoId,destinatarioId);
 }
}
