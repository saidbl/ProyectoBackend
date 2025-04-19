package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.CheckinRutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CheckinRutinaDao  extends JpaRepository<CheckinRutina,Integer>{
    @Query("SELECT c FROM CheckinRutina c WHERE c.jugador.id = :idJugador AND c.estado = 'COMPLETADA' ORDER BY c.fecha DESC, c.hora DESC")
    List<CheckinRutina> findByDeportistaIdAndEstado(@Param("idJugador") Long deportistaId);
}
