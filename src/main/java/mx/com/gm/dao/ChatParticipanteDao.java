package mx.com.gm.dao;

import mx.com.gm.domain.ChatParticipante;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatParticipanteDao extends JpaRepository<ChatParticipante,Long>{
     boolean existsByChatIdAndDeportistaId(Long chatId, Long deportistaId);
}
