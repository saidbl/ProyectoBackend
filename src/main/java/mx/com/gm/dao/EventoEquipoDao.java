package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.EventoEquipo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventoEquipoDao extends JpaRepository<EventoEquipo,Long>{
    List<EventoEquipo> findByEventoId(Long idequipo);
}
