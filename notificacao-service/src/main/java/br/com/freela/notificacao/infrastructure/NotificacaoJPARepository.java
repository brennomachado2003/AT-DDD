package br.com.freela.notificacao.infrastructure;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.UUID;
public interface NotificacaoJPARepository extends JpaRepository<NotificacaoEntity, UUID>{
    boolean existsByEventId(UUID eventId);
}
