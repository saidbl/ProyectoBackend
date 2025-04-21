package mx.com.gm.dao;

import jakarta.persistence.Tuple;
import java.util.List;
import mx.com.gm.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventoDao extends JpaRepository<Evento,Long> {
    List<Evento> findByDeporteId(Long idDeporte);
     @Query("SELECT e FROM Evento e " +
           "JOIN EventoEquipo ee ON e.id = ee.evento.id " +
           "JOIN Equipo eq ON ee.equipo.id = eq.id " +
           "JOIN JugadorEquipo je ON eq.id = je.equipo.id " +
           "JOIN Deportista d ON je.deportista.id = d.id " +
           "WHERE d.id = :deportistaId AND e.fecha >= CURRENT_DATE " +
           "ORDER BY e.fecha ASC")
    List<Evento> findProximosEventosByDeportistaId(@Param("deportistaId") Long deportistaId);
    
     @Query(value = """
        SELECT e.*, ef.* FROM evento e
        LEFT JOIN evento_fechas ef ON e.id = ef.id_evento
        WHERE e.id IN (
          SELECT ee.id_evento FROM evento_equipo ee
          WHERE ee.id_equipo IN (
            SELECT je.id_equipo FROM jugador_equipo je
            WHERE je.id_deportista = :deportistaId
          )
        )
        AND (e.fecha >= CURRENT_DATE OR e.recurrente = 1)
        ORDER BY e.id, COALESCE(ef.fecha, e.fecha) ASC
        """, nativeQuery = true)
    List<Object[]> findEventosYFechasByDeportistaNative(@Param("deportistaId") Long deportistaId);
}
