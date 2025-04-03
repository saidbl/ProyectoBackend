package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoDao extends JpaRepository<Evento,Long> {
    List<Evento> findByDeporteId(Long idDeporte);
}
