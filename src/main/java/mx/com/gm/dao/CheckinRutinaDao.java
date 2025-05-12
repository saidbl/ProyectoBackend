package mx.com.gm.dao;

import java.time.LocalDate;
import java.util.List;
import mx.com.gm.domain.CheckinRutina;
import mx.com.gm.domain.CheckinRutina.EstadoCheckin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CheckinRutinaDao  extends JpaRepository<CheckinRutina,Integer>{
    @Query("SELECT c FROM CheckinRutina c WHERE c.jugador.id = :idJugador AND c.estado = 'COMPLETADA' ORDER BY c.fecha DESC, c.hora DESC")
    List<CheckinRutina> findByDeportistaIdAndEstado(@Param("idJugador") Long deportistaId);
    
    @Query("SELECT COUNT(c) FROM CheckinRutina c WHERE c.jugador.id = :idJugador ORDER BY c.fecha DESC, c.hora DESC")
    long countByDeportistaId(@Param("idJugador") Long deportistaId);
    @Query("SELECT COUNT(c) FROM CheckinRutina c WHERE c.jugador.id = :idJugador AND c.estado = 'COMPLETADA' ORDER BY c.fecha DESC, c.hora DESC")
    long countByDeportistaIdAndEstado(@Param("idJugador") Long deportistaId);
    
     @Query("SELECT COUNT(c) FROM CheckinRutina c WHERE c.jugador.id = :jugadorId AND c.fecha >= :fechaInicio")
    long countByJugadorIdAndFechaAfter(
            @Param("jugadorId") Long jugadorId,
            @Param("fechaInicio") LocalDate fechaInicio);

    @Query("SELECT COUNT(c) FROM CheckinRutina c WHERE c.jugador.id = :jugadorId AND c.estado = :estado AND c.fecha >= :fechaInicio")
    long countByJugadorIdAndEstadoAndFechaAfter(
            @Param("jugadorId") Long jugadorId,
            @Param("estado") EstadoCheckin estado,
            @Param("fechaInicio") LocalDate fechaInicio);

    @Query("SELECT FUNCTION('DAYNAME', c.fecha) as dia, COUNT(c) as total " +
           "FROM CheckinRutina c " +
           "WHERE c.jugador.id = :jugadorId AND c.fecha >= :fechaInicio " +
           "GROUP BY FUNCTION('DAYNAME', c.fecha)")
    List<Object[]> countByJugadorIdGroupByDiaSemana(
            @Param("jugadorId") Long jugadorId,
            @Param("fechaInicio") LocalDate fechaInicio);
}
