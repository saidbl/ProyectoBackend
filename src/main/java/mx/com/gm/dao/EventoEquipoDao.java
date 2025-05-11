package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.Equipo;
import mx.com.gm.domain.EventoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface EventoEquipoDao extends JpaRepository<EventoEquipo,Long>{
    List<EventoEquipo> findByEventoId(Long idequipo);
    @Query("SELECT ee.equipo FROM EventoEquipo ee WHERE ee.evento.id = :eventoId")
    List<Equipo> findEquiposByEventoId(@Param("eventoId") Long eventoId);
}
