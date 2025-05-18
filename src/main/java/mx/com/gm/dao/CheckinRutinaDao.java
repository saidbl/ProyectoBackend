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
    
        @Query("SELECT c.estado, COUNT(c) FROM CheckinRutina c " +
               "WHERE c.fecha BETWEEN :inicio AND :fin " +
               "AND c.jugador.instructor.id = :instructorId " +
               "GROUP BY c.estado")
        List<Object[]> getResumenCompliance(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin,
            @Param("instructorId") Long instructorId
        );
        
        @Query("SELECT DAYOFWEEK(c.fecha), " +
               "SUM(CASE WHEN c.estado = 'COMPLETADA' THEN 1 ELSE 0 END) * 100.0 / COUNT(c) " +
               "FROM CheckinRutina c " +
               "WHERE c.fecha BETWEEN :inicio AND :fin " +
               "AND c.jugador.instructor.id = :instructorId " +
               "GROUP BY DAYOFWEEK(c.fecha)")
        List<Object[]> getCompliancePorDia(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin,
            @Param("instructorId") Long instructorId
        );
        
        @Query("SELECT c.jugador, COUNT(c) as total FROM CheckinRutina c JOIN c.jugador j JOIN j.instructor i WHERE c.estado IN ('INCOMPLETA', 'PENDIENTE') AND c.fecha BETWEEN :fechaInicio AND :fechaFin AND i.id = :idInstructor GROUP BY c.jugador.id ORDER BY total DESC")
        List<Object[]> getTopPendientes(LocalDate fechaInicio, LocalDate fechaFin, Long idInstructor);

        

}
