package mx.com.gm.dao;

import jakarta.transaction.Transactional;
import java.util.List;
import mx.com.gm.domain.EventoFecha;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoFechaDao extends JpaRepository<EventoFecha,Long>{
     List<EventoFecha> findByEventoId(Long idEvento);
     @Transactional
     void deleteByEventoId(Long idEvento);
}
