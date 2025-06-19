package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.Chat;
import mx.com.gm.domain.ChatTipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ChatDao extends JpaRepository<Chat,Long>{
    

    @Query("SELECT c FROM Chat c WHERE " +
           "(c.instructor.id = :instructorId AND c.tipo IN :tipos) OR " +
           "c.equipo IN (SELECT e FROM Equipo e WHERE e.instructor.id = :instructorId)")
    List<Chat> findByInstructorIdAndTipoIn(
        @Param("instructorId") Long instructorId,
        @Param("tipos") List<ChatTipo> tipos
    );

    // Para deportista
    @Query("SELECT c FROM Chat c WHERE " +
           "c.deportista.id = :deportistaId OR " +
           "c.instructor.id = :instructorId OR " +
           "c.id IN (SELECT cp.chat.id FROM ChatParticipante cp WHERE cp.deportista.id = :deportistaId)")
    List<Chat> findByDeportistaIdOrInstructorId(
        @Param("deportistaId") Long deportistaId,
        @Param("instructorId") Long instructorId
    );

    @Query("SELECT c FROM Chat c WHERE c.equipo.id IN :equipoIds")
List<Chat> findByEquipoIds(@Param("equipoIds") List<Long> equipoIds);
@Query(value = "SELECT je.id_equipo FROM jugador_equipo je WHERE je.id_deportista = :deportistaId", nativeQuery = true)
List<Long> findEquipoIdsByDeportistaId(@Param("deportistaId") Long deportistaId);

    
    @Query("SELECT c FROM Chat c WHERE c.equipo.instructor.id = :instructorId")
    List<Chat> findChatsByInstructorDelEquipo(@Param("instructorId") Long instructorId);

    List<Chat> findByOrganizacionId(Long organizacionId);
    
    List<Chat> findByDeportistaId(Long deportistaId);
    
    @Query("SELECT c FROM Chat c WHERE " +
       "(c.instructor.id = :instructorId AND c.deportista.id = :deportistaId AND c.tipo = :tipo) " +
       "OR (c.equipo.id = :equipoId AND c.tipo = :tipo)"+
        "OR (c.organizacion.id = :organizacionId AND c.tipo = :tipo  AND c.instructor.id = :instructorId )")
Chat findChatByUniqueKeys(@Param("instructorId") Long instructorId,
                          @Param("deportistaId") Long deportistaId,
                          @Param("equipoId") Long equipoId,
                          @Param("organizacionId")Long organizacionId,
                          @Param("tipo") ChatTipo tipo,
                          @Param("deporteId")Long deporteId);
}
