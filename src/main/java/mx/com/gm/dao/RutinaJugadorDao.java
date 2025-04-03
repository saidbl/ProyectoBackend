package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.RutinaJugador;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RutinaJugadorDao extends JpaRepository<RutinaJugador,Long>{
    List<RutinaJugador> findByDeportistaId(Long jugadorId);
}
