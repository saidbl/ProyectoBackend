package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EquipoDao extends JpaRepository<Equipo,Long>{
    List<Equipo> findByInstructorId(Long idInstructor);
    @Query(value = """
        SELECT e.* FROM equipo e
        JOIN jugador_equipo je ON e.id = je.id_equipo
        WHERE je.id_deportista = :jugadorId 
        """, nativeQuery = true)
    List<Equipo> findEquiposActivosByJugadorIdNative(@Param("jugadorId") Long jugadorId);
    
    @Query(value = "SELECT e.* FROM equipo e JOIN evento_equipo ee ON e.id = ee.id_equipo WHERE ee.id_evento = :eventoId", nativeQuery = true)
    List<Equipo> findEquiposByEventoIdNative(@Param("eventoId") Long eventoId);
}
