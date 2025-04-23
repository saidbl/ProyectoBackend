 package mx.com.gm.dao;

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
    
     @Query("SELECT e FROM Evento e WHERE e.organizacion.id = :idOrganizacion AND e.fecha >= CURRENT_DATE")
    List<Evento> findEventosFuturosByOrganizacion(@Param("idOrganizacion") Long idOrganizacion);
}
