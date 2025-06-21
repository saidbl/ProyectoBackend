package mx.com.gm.dao;

import jakarta.transaction.Transactional;
import java.util.List;
import mx.com.gm.domain.Equipo;
import mx.com.gm.domain.Evento;
import mx.com.gm.domain.EventoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface EventoEquipoDao extends JpaRepository<EventoEquipo,Long>{
    List<EventoEquipo> findByEventoId(Long idequipo);
    @Query("SELECT ee.equipo FROM EventoEquipo ee WHERE ee.evento.id = :eventoId")
    List<Equipo> findEquiposByEventoId(@Param("eventoId") Long eventoId);
     @Query("SELECT ee.evento FROM EventoEquipo ee WHERE ee.equipo.id = :equipoId")
    List<Evento> findEventosByEquipoId(@Param("equipoId") Long equipoId);
    @Modifying
@Transactional
@Query("DELETE FROM EventoEquipo ee WHERE ee.equipo.id = :equipoId AND ee.evento.id = :eventoId")
void deleteByEquipoIdAndEventoId(@Param("equipoId") Long equipoId, @Param("eventoId") Long eventoId);
}
