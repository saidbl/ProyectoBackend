package mx.com.gm.dao;

import mx.com.gm.domain.Mensaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MensajeDao extends JpaRepository<Mensaje,Long>{
    @Query("SELECT m FROM Mensaje m WHERE m.chat.id = :chatId ORDER BY m.fechaEnvio DESC")
    Page<Mensaje> findUltimosMensajes(@Param("chatId") Long chatId, Pageable pageable);
    
    @Query("SELECT m FROM Mensaje m WHERE m.chat.id = :chatId ORDER BY m.fechaEnvio DESC")
    Page<Mensaje> findByChatIdOrderByFechaEnvioDesc(
        @Param("chatId") Long chatId, 
        Pageable pageable
    );
    
    @Modifying
    @Query("UPDATE Mensaje m SET m.leido = true WHERE m.chat.id = :chatId AND m.leido = false AND m.remitenteId != :usuarioId")
    void marcarComoLeido(@Param("chatId") Long chatId, @Param("usuarioId") Long usuarioId);
    
}
