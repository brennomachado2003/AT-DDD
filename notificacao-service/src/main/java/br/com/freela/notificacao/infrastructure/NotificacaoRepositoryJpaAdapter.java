package br.com.freela.notificacao.infrastructure;

import br.com.freela.notificacao.domain.Notificacao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Repository
public class NotificacaoRepositoryJpaAdapter implements NotificacaoRepository{

    private final NotificacaoJPARepository jpa;

    public NotificacaoRepositoryJpaAdapter(NotificacaoJPARepository jpa) { this.jpa = jpa; }

    @Override
    public Notificacao salvar(Notificacao notificacao) {
        NotificacaoEntity notificacaoEntity = NotificacaoMapper.toEntity(notificacao);
        NotificacaoEntity savo = jpa.save(notificacaoEntity);
        return NotificacaoMapper.toDomain(savo);
    }

    @Override
    public List<Notificacao> listarNotificacao(){
        List<NotificacaoEntity> notificacaoEntity = jpa.findAll();
        return notificacaoEntity.stream().map(NotificacaoMapper::toDomain).toList();
    }

    @Override
    public boolean verificarExistenciaEvento(UUID eventoId){
        return jpa.existsByEventId(eventoId);
    }

}
