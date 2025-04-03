package mx.com.gm.dao;

import java.util.List;
import mx.com.gm.domain.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PosicionDao extends JpaRepository<Posicion,Long>{
    List<Posicion> findByDeporteId(Long deporteId);
}
